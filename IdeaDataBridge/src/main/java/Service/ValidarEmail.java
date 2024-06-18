package Service;

import Controller.CriadorController;
import Controller.LoginController;
import Model.Gerente;
import Model.Totem;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ValidarEmail {
    static CriadorController criador = new CriadorController();
    static Totem totem = new Totem();
    static Scanner sc = new Scanner(System.in);

    private static final String emailHost = "smtp.gmail.com";
    private static final String emailPort = "587";
    private static final String emailUsername = "vitortigre00@gmail.com";
    private static final String emailPassword = "ydok lkbw kfzx rzfk";

    private static int pass;

    public static void credenciais(ConexaoMySqlService conMySQL, ConexaoSqlServerService conSQLServer) throws IOException {

        System.out.print("E-mail: ");
        String userEmail = sc.nextLine();

        System.out.print("Senha: ");
        String userSenha = sc.nextLine();


        Log.createLog(ValidarEmail.class, "INFO", "Tentativa de login. E-mail: " + userEmail);

        String sql = "SELECT * FROM gerente WHERE email = ? AND senha = ?";
        Object[] params = {userEmail, userSenha};
        List<Gerente> gerenteEncontrado = conSQLServer.getCon().query(sql, params, new BeanPropertyRowMapper<>(Gerente.class));
        Gerente gerente = (gerenteEncontrado.isEmpty()) ? null : gerenteEncontrado.get(0);

        if(gerente.getEmail() != null) {
            Log.createLog(ValidarEmail.class, "OK", userEmail + " encontrado no banco de dados.");
            pass = ThreadLocalRandom.current().nextInt(100000, 1000000);
            enviarEmail(userEmail, pass, conMySQL, conSQLServer);
        }else {
            Log.createLog(ValidarEmail.class, "ERRO", "E-mail não está no banco de dados.");
            System.out.println("E-mail inexistente no banco de dados.");
        }
    }

    private static void enviarEmail(String userEmail, int pass, ConexaoMySqlService conMySQL, ConexaoSqlServerService conSQLServer) throws IOException {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", emailHost);
            props.put("mail.smtp.port", emailPort);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", emailHost);

            Log.createLog(ValidarEmail.class, "INFO", "Configurando perfil de e-mail para envio.");
            System.out.print("Aguarde.");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userEmail, emailUsername);
                }
            });

            System.out.print(".");

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailUsername));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(userEmail));
            message.setSubject("Validação de E-mail iDEA+");
            message.setContent("""
                    <h2 style="color: orange;">Olá!</h2>
                    <h3>Aqui está o número de validação para login no totem...</h3>
                    <h3>%s</h3>
                    <br><br>
                    <p>Da equipe iDEA7. <3</p>""".formatted(pass), "text/html; charset=utf-8");

            Log.createLog(ValidarEmail.class, "INFO", "Mensagem de e-mail pronta para envio.");
            System.out.println(".");

            Transport transport = session.getTransport("smtp");
            transport.connect(emailHost, Integer.parseInt(emailPort), emailUsername, emailPassword);
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println("O número de verificação foi enviado no seu e-mail. Cheque sua caixa de spam.");
            Log.createLog(ValidarEmail.class, "OK", "Número de verificação enviado para o usuário de e-mail " + userEmail);
            confirmarCodigo(pass, conMySQL, conSQLServer);
        } catch (MessagingException e) {
            System.out.println("Houve um erro ao enviar o e-mail para sua conta." + e.getMessage());
            Log.createLog(ValidarEmail.class, "ERRO", "Erro ao enviar e-mail para conta do usuário de e-mail " + userEmail);
        }
    }

    private static void confirmarCodigo(int pass, ConexaoMySqlService conMySQL, ConexaoSqlServerService conSQLServer) throws IOException {
        System.out.print("Número de verificação: ");
        int nmrDigitado = sc.nextInt();

        if (nmrDigitado == pass) {
            System.out.println("Conectando...");
            Log.createLog(ValidarEmail.class, "OK", "Validação de número de validação de login feita com sucesso");
            LoginController.login(conMySQL, conSQLServer, totem);
        } else {
            System.out.println("Número errado. Tente novamente.");
            confirmarCodigo(pass, conMySQL, conSQLServer);
        }
    }

}

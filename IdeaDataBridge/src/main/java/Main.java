import Controller.CriadorController;
import Service.Reinicializacao;
import Service.ValidarEmail;

import java.io.IOException;

public class Main {
    static CriadorController criador = new CriadorController();
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("""
        
                                            ::::::::::: :::::::::  ::::::::::     :::                  \s
                                                :+:     :+:    :+: :+:          :+: :+:        :+:     \s
                                                +:+     +:+    +:+ +:+         +:+   +:+       +:+     \s
                                                +#+     +#+    +:+ +#++:++#   +#++:++#++: +#++:++#++:++\s
                                                +#+     +#+    +#+ +#+        +#+     +#+      +#+     \s
                                                #+#     #+#    #+# #+#        #+#     #+#      #+#     \s
                                            ########### #########  ########## ###     ###              \s
                                                                                                                                            by: Vitor Tigre
                """);

        Thread.sleep(3000);


        criador.criarConexaoMySQL();

        ValidarEmail.credenciais(criador.criarConexaoMySQL(), criador.criarConexaoSQLServer());
    }
}

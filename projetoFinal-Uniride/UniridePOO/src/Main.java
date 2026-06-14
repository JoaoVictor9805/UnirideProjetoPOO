import javafx.application.Application;
import javafx.stage.Stage;
import view.TelaSobre;

public class Main extends Application {

        @Override
        public void start(Stage palcoPrincipal) { // palcoPrincipal é a janela física vazia que o JavaFX te deu de presente.
            TelaSobre telaInicial = new TelaSobre(palcoPrincipal); // telaInicial: É a lógica que desenha o seu sistema por dentro. Foi criado por você.
            palcoPrincipal.setScene(telaInicial.desenharTela());
            palcoPrincipal.setTitle("UniRide - Início");
            palcoPrincipal.show();
        }


}
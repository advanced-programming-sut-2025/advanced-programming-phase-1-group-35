package Controller;

import GraphicView.ForgotPasswordUI;
import GraphicView.LoginUI;
import Model.SHA256;
import Model.enums.Dialogues;
import Model.enums.Regexes;
import com.StardewValley.Main;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ForgotPasswordMenuController extends Controller {
    ForgotPasswordUI view ;

    public void setView(ForgotPasswordUI view) {
        this.view = view;
    }

    public void handleForgetPassword() {
        if(view != null && view.getAdvanceButton().isChecked()){
            if(!view.getAnswerTextField().getText().equalsIgnoreCase(view.getUser().getSecurityAnswer())){
                showErrorDialog(Dialogues.ErrorWrongAnswer.title, Dialogues.ErrorWrongAnswer.message);
                resetFormFields();
            }
            else if(Regexes.Password.getMatcher(view.getNewPasswordTextField().getText()) == null){
                showErrorDialog(Dialogues.ErrorInvalidPassword.title, Dialogues.ErrorInvalidPassword.message );
                resetFormFields();
            }
            else {
                view.getUser().setPassword(SHA256.hashString(view.getNewPasswordTextField().getText()));
                Main.getGame().getScreen().dispose();
                Main.getGame().setScreen(new LoginUI(new LoginMenuController()));
            }
        }
    }

    public void handleGoBack() {
        if(view != null && view.getBackButton().isChecked()){
            Main.getGame().getScreen().dispose();
            Main.getGame().setScreen(new LoginUI(new LoginMenuController()));
        }
    }

    private void resetFormFields() {
        view.getNewPasswordTextField().setMessageText(view.getNewPasswordTextField().getMessageText());
        view.getAdvanceButton().setChecked(false);
    }
}

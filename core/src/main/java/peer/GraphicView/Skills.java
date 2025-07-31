package peer.GraphicView;

import common.Model.App;
import common.Model.GameAssetManager;
import common.Model.Tools.SkillLevel;
import common.Model.User;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Skills {

    private Table skillsTable;
    private Skin skin;
    private boolean isSkillsVisible = false;

    public Skills(Stage stage) {
        this.skin = GameAssetManager.getDefaultSkin();
        createSkillsTable();
        stage.addActor(skillsTable);
    }

    private void createSkillsTable() {
        skillsTable = new Table(skin);
        skillsTable.setBackground("window");
        skillsTable.pad(25);
        skillsTable.setVisible(isSkillsVisible);

        User player = App.getCurrentGame().getPlayingUser();

        Label titleLabel = new Label("Player Skills", skin, "title");
        skillsTable.add(titleLabel).colspan(2).center().padBottom(20);
        skillsTable.row();

        addSkillToTable("Farming", player.getFarmingSkill());
        addSkillToTable("Fishing", player.getFishingSkill());
        addSkillToTable("Foraging", player.getForagingSkill());
        addSkillToTable("Mining", player.getMiningSkill());

        skillsTable.pack();
        skillsTable.setPosition(
            (Gdx.graphics.getWidth() / 2f) - (skillsTable.getWidth() / 2f),
            (Gdx.graphics.getHeight() / 2f) - (skillsTable.getHeight() / 2f)
        );
    }

    private void addSkillToTable(String skillName, SkillLevel skillLevel) {
        Label nameLabel = new Label(skillName + ":", skin);
        Label levelLabel = new Label("Level " + skillLevel.getCurrentLevel() + " (" + skillLevel.getCurrentXp() + " XP)", skin);

        skillsTable.add(nameLabel).left().padRight(20);
        skillsTable.add(levelLabel).right();
        skillsTable.row().padTop(10);
    }

    public void toggleVisibility() {
        isSkillsVisible = !isSkillsVisible;
        skillsTable.setVisible(isSkillsVisible);
    }

    public void setVisible(boolean visible) {
        isSkillsVisible = visible;
        skillsTable.setVisible(visible);
    }
}

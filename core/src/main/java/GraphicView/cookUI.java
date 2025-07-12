package GraphicView;

import Controller.InGameMenu.CookingController;
import Model.App;
import Model.Rect;
import Model.enums.CookingRecipes;
import com.StardewValley.Main;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class cookUI implements Screen {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Texture toggledPictureTexture;
    private boolean isPictureVisible = false;
    private final int y = 65;

    private final Texture bakedFishTexture = new Texture(Gdx.files.internal("assets/recipe/Baked_Fish.png"));
    private final Texture breadTexture = new Texture(Gdx.files.internal("assets/recipe/Bread.png"));
    private final Texture cookieTexture = new Texture(Gdx.files.internal("assets/recipe/Cookie.png"));
    private final Texture dishOfTheSeaTexture = new Texture(Gdx.files.internal("assets/recipe/Dish_O_The_Sea.png"));
    private final Texture farmersLunchTexture = new Texture(Gdx.files.internal("assets/recipe/Farmers_Lunch.png"));
    private final Texture friedEggTexture = new Texture(Gdx.files.internal("assets/recipe/Fried_Egg.png"));
    private final Texture fruitSaladTexture = new Texture(Gdx.files.internal("assets/recipe/Fruit_Salad.png"));
    private final Texture makiRollTexture = new Texture(Gdx.files.internal("assets/recipe/Maki_Roll.png"));
    private final Texture minersTreatTexture = new Texture(Gdx.files.internal("assets/recipe/Miners_Treat.png"));
    private final Texture omeletTexture = new Texture(Gdx.files.internal("assets/recipe/Omelet.png"));
    private final Texture pancakesTexture = new Texture(Gdx.files.internal("assets/recipe/Pancakes.png"));
    private final Texture pizzaTexture = new Texture(Gdx.files.internal("assets/recipe/Pizza.png"));
    private final Texture pumpkinPieTexture = new Texture(Gdx.files.internal("assets/recipe/Pumpkin_Pie.png"));
    private final Texture redPlateTexture = new Texture(Gdx.files.internal("assets/recipe/Red_Plate.png"));
    private final Texture saladTexture = new Texture(Gdx.files.internal("assets/recipe/Salad.png"));
    private final Texture salmonDinnerTexture = new Texture(Gdx.files.internal("assets/recipe/Salmon_Dinner.png"));
    private final Texture seafoamPuddingTexture = new Texture(Gdx.files.internal("assets/recipe/Seafoam_Pudding.png"));
    private final Texture spaghettiTexture = new Texture(Gdx.files.internal("assets/recipe/Spaghetti.png"));
    private final Texture survivalBurgerTexture = new Texture(Gdx.files.internal("assets/recipe/Survival_Burger.png"));
    private final Texture tortillaTexture = new Texture(Gdx.files.internal("assets/recipe/Tortilla.png"));
    private final Texture tripleShotEspressoTexture = new Texture(Gdx.files.internal("assets/recipe/Triple_Shot_Espresso.png"));
    private final Texture troutSoupTexture = new Texture(Gdx.files.internal("assets/recipe/Trout_Soup.png"));
    private final Texture vegetableMedleyTexture = new Texture(Gdx.files.internal("assets/recipe/Vegetable_Medley.png"));

    private Rect bakedFishRect;
    private Rect breadRect;
    private Rect cookieRect;
    private Rect dishOfTheSeaRect;
    private Rect farmersLunchRect;
    private Rect friedEggRect;
    private Rect fruitSaladRect;
    private Rect makiRollRect;
    private Rect minersTreatRect;
    private Rect omeletRect;
    private Rect pancakesRect;
    private Rect pizzaRect;
    private Rect pumpkinPieRect;
    private Rect redPlateRect;
    private Rect saladRect;
    private Rect salmonDinnerRect;
    private Rect seafoamPuddingRect;
    private Rect spaghettiRect;
    private Rect survivalBurgerRect;
    private Rect tortillaRect;
    private Rect tripleShotEspressoRect;
    private Rect troutSoupRect;
    private Rect vegetableMedleyRect;

    private Stage stage;
    private Main game;
    private final CookingController cookingController;

    private static final int RECIPES_PER_ROW = 12;
    private static final int RECIPE_ICON_SIZE = 49;
    private static final int RECIPE_PADDING_X = 15;
    private static final int RECIPE_PADDING_Y = 15;

    public cookUI(Main game) {
        this.game = game;
        cookingController = new CookingController();
        cookingController.showCookingRecipes();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());
        toggledPictureTexture = new Texture(Gdx.files.internal("assets/shelf2.png"));

        bakedFishRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        breadRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        cookieRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        dishOfTheSeaRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        farmersLunchRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        friedEggRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        fruitSaladRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        makiRollRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        minersTreatRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        omeletRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        pancakesRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        pizzaRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        pumpkinPieRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        redPlateRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        saladRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        salmonDinnerRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        seafoamPuddingRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        spaghettiRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        survivalBurgerRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        tortillaRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        tripleShotEspressoRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        troutSoupRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
        vegetableMedleyRect = new Rect(0, 0, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);


        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.C) {
                    isPictureVisible = !isPictureVisible;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (isPictureVisible && button == Input.Buttons.LEFT) {
                    int libGdxY = Gdx.graphics.getHeight() - screenY;

                    if (bakedFishRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.BAKED_FISH.name());
                        return true;
                    } else if (breadRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.BREAD.name());
                        return true;
                    } else if (cookieRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.COOKIE.name());
                        return true;
                    } else if (dishOfTheSeaRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.DISH_O_THE_SEA.name());
                        return true;
                    } else if (farmersLunchRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.FARMERS_LUNCH.name());
                        return true;
                    } else if (friedEggRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.FRIED_EGG.name());
                        return true;
                    } else if (fruitSaladRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.FRUIT_SALAD.name());
                        return true;
                    } else if (makiRollRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.MAKI_ROLL.name());
                        return true;
                    } else if (minersTreatRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.MINERS_TREAT.name());
                        return true;
                    } else if (omeletRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.OMELET.name());
                        return true;
                    } else if (pancakesRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.PANCAKES.name());
                        return true;
                    } else if (pizzaRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.PIZZA.name());
                        return true;
                    } else if (pumpkinPieRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.PUMPKIN_PIE.name());
                        return true;
                    } else if (redPlateRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.RED_PLATE.name());
                        return true;
                    } else if (saladRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.SALAD.name());
                        return true;
                    } else if (salmonDinnerRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.SALMON_DINNER.name());
                        return true;
                    } else if (seafoamPuddingRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.SEAFOAM_PUDDING.name());
                        return true;
                    } else if (spaghettiRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.SPAGHETTI.name());
                        return true;
                    } else if (survivalBurgerRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.SURVIVAL_BURGER.name());
                        return true;
                    } else if (tortillaRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.TORTILLA.name());
                        return true;
                    } else if (tripleShotEspressoRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.TRIPLE_SHOT_ESPRESSO.name());
                        return true;
                    } else if (troutSoupRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.TROUT_SOUP.name());
                        return true;
                    } else if (vegetableMedleyRect.contains(screenX, libGdxY)) {
                        cookingController.cook(CookingRecipes.VEGETABLE_MEDLEY.name());
                        return true;
                    }
                }
                return false;
            }
        }));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        if (isPictureVisible) {
            batch.begin();
            float shelfX = (Gdx.graphics.getWidth() - toggledPictureTexture.getWidth()) / 2f;
            batch.draw(toggledPictureTexture, shelfX, y - 10);
            batch.end();

            drawRecipes();
        }

        stage.draw();
    }

    private void drawRecipes() {
        if (App.getLoggedInUser() == null || App.getCurrentGame().getPlayingUser() == null || App.getCurrentGame().getPlayingUser().learnedRecipes == null) {
            return;
        }

        float shelfWidth = toggledPictureTexture.getWidth();
        float shelfX = (Gdx.graphics.getWidth() - shelfWidth) / 2f;

        float rowContentWidth = (RECIPES_PER_ROW * RECIPE_ICON_SIZE) + ((RECIPES_PER_ROW - 1) * RECIPE_PADDING_X);
        float startRecipeX = (shelfX + (shelfWidth - rowContentWidth) / 2f) - 4;

        int currentRecipeIndex = 0;
        batch.begin();

        // Iterate through learned recipes and draw them
        for (CookingRecipes recipe : App.getLoggedInUser().learnedRecipes) {
            Texture recipeTexture = null;
            Rect recipeRect = null;

            switch (recipe) {
                case BAKED_FISH:
                    recipeTexture = bakedFishTexture;
                    recipeRect = bakedFishRect;
                    break;
                case BREAD:
                    recipeTexture = breadTexture;
                    recipeRect = breadRect;
                    break;
                case COOKIE:
                    recipeTexture = cookieTexture;
                    recipeRect = cookieRect;
                    break;
                case DISH_O_THE_SEA:
                    recipeTexture = dishOfTheSeaTexture;
                    recipeRect = dishOfTheSeaRect;
                    break;
                case FARMERS_LUNCH:
                    recipeTexture = farmersLunchTexture;
                    recipeRect = farmersLunchRect;
                    break;
                case FRIED_EGG:
                    recipeTexture = friedEggTexture;
                    recipeRect = friedEggRect;
                    break;
                case FRUIT_SALAD:
                    recipeTexture = fruitSaladTexture;
                    recipeRect = fruitSaladRect;
                    break;
                case MAKI_ROLL:
                    recipeTexture = makiRollTexture;
                    recipeRect = makiRollRect;
                    break;
                case MINERS_TREAT:
                    recipeTexture = minersTreatTexture;
                    recipeRect = minersTreatRect;
                    break;
                case OMELET:
                    recipeTexture = omeletTexture;
                    recipeRect = omeletRect;
                    break;
                case PANCAKES:
                    recipeTexture = pancakesTexture;
                    recipeRect = pancakesRect;
                    break;
                case PIZZA:
                    recipeTexture = pizzaTexture;
                    recipeRect = pizzaRect;
                    break;
                case PUMPKIN_PIE:
                    recipeTexture = pumpkinPieTexture;
                    recipeRect = pumpkinPieRect;
                    break;
                case RED_PLATE:
                    recipeTexture = redPlateTexture;
                    recipeRect = redPlateRect;
                    break;
                case SALAD:
                    recipeTexture = saladTexture;
                    recipeRect = saladRect;
                    break;
                case SALMON_DINNER:
                    recipeTexture = salmonDinnerTexture;
                    recipeRect = salmonDinnerRect;
                    break;
                case SEAFOAM_PUDDING:
                    recipeTexture = seafoamPuddingTexture;
                    recipeRect = seafoamPuddingRect;
                    break;
                case SPAGHETTI:
                    recipeTexture = spaghettiTexture;
                    recipeRect = spaghettiRect;
                    break;
                case SURVIVAL_BURGER:
                    recipeTexture = survivalBurgerTexture;
                    recipeRect = survivalBurgerRect;
                    break;
                case TORTILLA:
                    recipeTexture = tortillaTexture;
                    recipeRect = tortillaRect;
                    break;
                case TRIPLE_SHOT_ESPRESSO:
                    recipeTexture = tripleShotEspressoTexture;
                    recipeRect = tripleShotEspressoRect;
                    break;
                case TROUT_SOUP:
                    recipeTexture = troutSoupTexture;
                    recipeRect = troutSoupRect;
                    break;
                case VEGETABLE_MEDLEY:
                    recipeTexture = vegetableMedleyTexture;
                    recipeRect = vegetableMedleyRect;
                    break;
                default:
                    System.err.println("Unknown recipe: " + recipe.name());
                    continue;
            }

            if (recipeTexture != null && recipeRect != null) {
                int row = currentRecipeIndex / RECIPES_PER_ROW;
                int col = currentRecipeIndex % RECIPES_PER_ROW;

                float recipeX = startRecipeX + (col * (RECIPE_ICON_SIZE + RECIPE_PADDING_X));
                float recipeY = y + (row * (RECIPE_ICON_SIZE + RECIPE_PADDING_Y));

                batch.draw(recipeTexture, recipeX, recipeY, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);

                recipeRect.set(recipeX, recipeY, RECIPE_ICON_SIZE, RECIPE_ICON_SIZE);
            }
            currentRecipeIndex++;
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        toggledPictureTexture.dispose();

        bakedFishTexture.dispose();
        breadTexture.dispose();
        cookieTexture.dispose();
        dishOfTheSeaTexture.dispose();
        farmersLunchTexture.dispose();
        friedEggTexture.dispose();
        fruitSaladTexture.dispose();
        makiRollTexture.dispose();
        minersTreatTexture.dispose();
        omeletTexture.dispose();
        pancakesTexture.dispose();
        pizzaTexture.dispose();
        pumpkinPieTexture.dispose();
        redPlateTexture.dispose();
        saladTexture.dispose();
        salmonDinnerTexture.dispose();
        seafoamPuddingTexture.dispose();
        spaghettiTexture.dispose();
        survivalBurgerTexture.dispose();
        tortillaTexture.dispose();
        tripleShotEspressoTexture.dispose();
        troutSoupTexture.dispose();
        vegetableMedleyTexture.dispose();

        stage.dispose();
    }
}

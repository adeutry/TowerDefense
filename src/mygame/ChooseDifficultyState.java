package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.ui.Picture;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class ChooseDifficultyState extends AbstractAppState implements ActionListener, ScreenController {

    Main main;
    private Node ground, diffPictures;
    private Tower computerTower;
    private String newMappings[];
    float pictureHeight = .25f;
    float pictureOffset = .1f;
    float pictureSpacing = .2f;
    int selected = 1;
    
    Picture difficultyBanner, easyPic, mediumPic, hardPic;
    Picture easyFadedPic, mediumFadedPic, hardFadedPic;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
      
        this.main = (Main)app;
        main.getFlyByCamera().setEnabled(false);
        //Keys
        InputManager inputManager = main.getInputManager();
        inputManager.addMapping("select", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("up", new KeyTrigger(KeyInput.KEY_UP));
        inputManager.addMapping("down", new KeyTrigger(KeyInput.KEY_DOWN));
        inputManager.addListener(this, newMappings = new String[]{"select","up","down"});
     
        diffPictures = new Node("diffPictures");
        main.getGuiNode().attachChild(diffPictures);
        
        //create choose difficulty banner
        difficultyBanner = new Picture("diffiultyBanner");
        difficultyBanner.setImage(app.getAssetManager(), "Textures/UI/difficultyBanner.png", true);
        difficultyBanner.setWidth(main.getAppSettings().getWidth()*.95f);
        difficultyBanner.setHeight(main.getAppSettings().getHeight()*.4f);
        difficultyBanner.setPosition(
                 main.getAppSettings().getWidth()*.025f, 
           main.getAppSettings().getHeight()*.65f);
       
        
        //easy picture
        easyPic = new Picture("diffiultyBanner");
        easyPic.setImage(app.getAssetManager(), "Textures/UI/easy.png", true);
        easyPic.setWidth(main.getAppSettings().getWidth()*.30f);
        easyPic.setHeight(main.getAppSettings().getHeight()*pictureHeight);
        easyPic.setPosition(
                 main.getAppSettings().getWidth()*.35f, 
           main.getAppSettings().getHeight()*(pictureOffset + 2*pictureSpacing));
        
        
        //medium picture
        mediumPic = new Picture("diffiultyBanner");
        mediumPic.setImage(app.getAssetManager(), "Textures/UI/medium.png", true);
        mediumPic.setWidth(main.getAppSettings().getWidth()*.4f);
        mediumPic.setHeight(main.getAppSettings().getHeight()*pictureHeight);
        mediumPic.setPosition(
                 main.getAppSettings().getWidth()*.3f, 
           main.getAppSettings().getHeight()*(pictureOffset + pictureSpacing));
        
        
        //hard picture
        hardPic = new Picture("diffiultyBanner");
        hardPic.setImage(app.getAssetManager(), "Textures/UI/hard.png", true);
        hardPic.setWidth(main.getAppSettings().getWidth()*.3f);
        hardPic.setHeight(main.getAppSettings().getHeight()*pictureHeight);
        hardPic.setPosition(
                 main.getAppSettings().getWidth()*.35f, 
           main.getAppSettings().getHeight()*(pictureOffset));
        
        
         //easyFaded picture
        easyFadedPic = new Picture("diffiultyBanner");
        easyFadedPic.setImage(app.getAssetManager(), "Textures/UI/easyFaded.png", true);
        easyFadedPic.setWidth(main.getAppSettings().getWidth()*.30f);
        easyFadedPic.setHeight(main.getAppSettings().getHeight()*pictureHeight);
        easyFadedPic.setPosition(
                 main.getAppSettings().getWidth()*.35f, 
           main.getAppSettings().getHeight()*(pictureOffset + 2*pictureSpacing));
        
        
        //mediumFaded picture
        mediumFadedPic = new Picture("diffiultyBanner");
        mediumFadedPic.setImage(app.getAssetManager(), "Textures/UI/mediumFaded.png", true);
        mediumFadedPic.setWidth(main.getAppSettings().getWidth()*.4f);
        mediumFadedPic.setHeight(main.getAppSettings().getHeight()*pictureHeight);
        mediumFadedPic.setPosition(
                 main.getAppSettings().getWidth()*.3f, 
           main.getAppSettings().getHeight()*(pictureOffset + pictureSpacing));
       
        
        //hardFaded picture
        hardFadedPic = new Picture("diffiultyBanner");
        hardFadedPic.setImage(app.getAssetManager(), "Textures/UI/hardFaded.png", true);
        hardFadedPic.setWidth(main.getAppSettings().getWidth()*.3f);
        hardFadedPic.setHeight(main.getAppSettings().getHeight()*pictureHeight);
        hardFadedPic.setPosition(
                 main.getAppSettings().getWidth()*.35f, 
           main.getAppSettings().getHeight()*(pictureOffset));
        
        
        main.getGuiNode().attachChild(difficultyBanner);
        diffPictures.attachChild(easyPic);
        diffPictures.attachChild(mediumFadedPic);
        diffPictures.attachChild(hardFadedPic);
				
    }

    @Override
    public void update(float tpf) {
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("select") && isPressed) {
            switch (selected){
          case 1:
            main.difficulty = "easy";
            break;
          case 2:
            main.difficulty = "medium";
            break;
          case 3:
            main.difficulty = "hard";
            break;
        }
          
            //transition to the round state
            startGame();
        }
        
        if(name.equals("up") && isPressed)
        {
          if(selected == 1)
          {
            selected = 3;
          }else{
            selected--;
          }
          updatePictures();
        }
        
        if(name.equals("down") && isPressed)
        {
          if(selected ==3 )
          {
            selected = 1;
          }else{
            selected++;
          }
          updatePictures();
        }
    }
      
    public void updatePictures(){
      diffPictures.detachAllChildren();
      
      if(selected == 1){
        diffPictures.attachChild(easyPic);
      }else{
        diffPictures.attachChild(easyFadedPic);
      }
      
      if(selected == 2){
        diffPictures.attachChild(mediumPic);
      }else{
        diffPictures.attachChild(mediumFadedPic);
      }
      
      if(selected == 3){
        diffPictures.attachChild(hardPic);
      }else{
        diffPictures.attachChild(hardFadedPic);
      }
    }

    public void startGame() {
        //transitiong to the round state
        System.out.println("Beginning game...");
        AppStateManager asm = main.getStateManager();
        BreakState breakState = new BreakState();
        asm.detach(this);
        asm.attach(breakState);
    }
    
   


    @Override
    public void cleanup() {
        System.out.println("cleaning up break screen...");
        main.getGuiNode().detachAllChildren();
        main.getGuiViewPort().removeProcessor(main.getNiftyDisplay());
        main.deleteInputMappings(newMappings);
    }

  

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

  public void bind(Nifty nifty, Screen screen) {
   
  }
}
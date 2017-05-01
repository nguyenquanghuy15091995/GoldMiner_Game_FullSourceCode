package vn.edu.tdt.finalproject.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import vn.edu.tdt.finalproject.Application;

public class SplashDoors {
    private static Texture splashDoorLeft = new Texture("images/backgrounds/splashdoor.png");
    private static Texture splashDoorRight = new Texture("images/backgrounds/splashdoor.png");
    private static float[] doorDirectionX = {0, Application.DESKTOP_WIDTH/2};
    private static TextNoBackground loadingText = new TextNoBackground(Color.GOLD, 30);

    public static void setDoorOpen(){
        doorDirectionX[0] = (-1.0f)*Application.DESKTOP_WIDTH/2;
        doorDirectionX[1] = Application.DESKTOP_WIDTH;
    }

    public static void setDoorClose(){
        doorDirectionX[0] = 0;
        doorDirectionX[1] = Application.DESKTOP_WIDTH/2;
    }

    public static boolean checkDoorClose(){
        if((doorDirectionX[0] == 0) && (doorDirectionX[1] == Application.DESKTOP_WIDTH/2)){
            return true;
        }
        return false;
    }

    public static boolean checkDoorOpen(){
        if((doorDirectionX[0] == (-1.0f)*Application.DESKTOP_WIDTH/2) && (doorDirectionX[1] == Application.DESKTOP_WIDTH)){
            return true;
        }
        return false;
    }

    public static void openTheSplashDoor(float moveSpeed){
        if(doorDirectionX[0] > (-1.0f)*Application.DESKTOP_WIDTH/2){
            doorDirectionX[0] -= moveSpeed*ScreenConstants.TRANSFORM_X;
        }
        if(doorDirectionX[1] <= Application.DESKTOP_WIDTH){
            doorDirectionX[1] += moveSpeed*ScreenConstants.TRANSFORM_X;
        }
        if(doorDirectionX[0] < (-1.0f)*Application.DESKTOP_WIDTH/2){
            doorDirectionX[0] = (-1.0f)*Application.DESKTOP_WIDTH/2;
        }
        if(doorDirectionX[1] > Application.DESKTOP_WIDTH){
            doorDirectionX[1] = Application.DESKTOP_WIDTH;
        }
    }

    public static void closeTheSplashDoor(float moveSpeed){
        if(doorDirectionX[0] < 0){
            doorDirectionX[0] += moveSpeed*ScreenConstants.TRANSFORM_X;
        }
        if(doorDirectionX[1] > Application.DESKTOP_WIDTH/2){
            doorDirectionX[1] -= moveSpeed*ScreenConstants.TRANSFORM_X;
        }
        if(doorDirectionX[0] > 0){
            doorDirectionX[0] = 0;
        }
        if(doorDirectionX[1] < Application.DESKTOP_WIDTH/2){
            doorDirectionX[1] = Application.DESKTOP_WIDTH/2;
        }
    }

    public static void drawDoor(Batch batch){
        batch.draw(splashDoorLeft, doorDirectionX[0], 0f, Application.DESKTOP_WIDTH/2, Application.DESKTOP_HEIGHT);
        batch.draw(splashDoorRight, doorDirectionX[1], 0f, Application.DESKTOP_WIDTH/2, Application.DESKTOP_HEIGHT);
        if(checkDoorClose()){
            loadingText.drawText(batch, "Loading...", Application.DESKTOP_WIDTH/2 - 80f, Application.DESKTOP_HEIGHT/2 + 10f);
        }
    }

    public static void dispose(){
        splashDoorLeft.dispose();
        splashDoorRight.dispose();
        loadingText.dispose();
    }
}

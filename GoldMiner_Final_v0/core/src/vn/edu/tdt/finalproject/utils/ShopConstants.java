package vn.edu.tdt.finalproject.utils;

import vn.edu.tdt.finalproject.actors.ActorItem;
import vn.edu.tdt.finalproject.sounds.SoundEffect;

public class ShopConstants {

    public static final long TIME_BONUS = 30;

    public static final float SODA_POWER = 1f * (int) ScreenConstants.TRANSFORM_Y;

    private static ActorItem[] LIST_ITEMS = {
            new ActorItem("animations/buttons/shop/itembomb.atlas", 0, 10f, 80f, 90f, 90f, ActorItem.ItemTag.BOMB),
            new ActorItem("animations/buttons/shop/itemclock.atlas", 0, 120f, 80f, 90f, 90f, ActorItem.ItemTag.CLOCK),
            new ActorItem("animations/buttons/shop/itemsoda.atlas", 0, 230f, 80f, 90f, 90f, ActorItem.ItemTag.SODA)
    };

    public static ActorItem[] getListItems() {
        return LIST_ITEMS;
    }

    public static void resetListItem(){
        for (int i = 0; i < LIST_ITEMS.length; i++){
            LIST_ITEMS[i].setEnable(true);
            LIST_ITEMS[i].getSoundBuy().setSoundKind(SoundEffect.SoundKind.ONE_TIME);
        }
    }

    public static void disposeAllItem(){
        int n = LIST_ITEMS.length;
        for (int i = 0; i < n; i++){
            if(LIST_ITEMS[i] != null) {
                LIST_ITEMS[i].remove();
            }
        }
    }
}

package ru.cubos.server.system.apps.systemApps;

import ru.cubos.commonHelpers.Buttons;
import ru.cubos.server.Server;
import ru.cubos.commonHelpers.Colors;
import ru.cubos.server.system.apps.App;
import ru.cubos.server.system.events.TouchDownEvent;
import ru.cubos.server.system.events.TouchMoveEvent;
import ru.cubos.server.system.events.TouchTapEvent;
import ru.cubos.server.system.events.TouchUpEvent;
import ru.cubos.server.system.views.DesktopIconView;
import ru.cubos.server.system.views.IconView;
import ru.cubos.server.system.views.TextView;
import ru.cubos.server.system.views.View;
import ru.cubos.server.system.views.containers.TabelContainer;
import ru.cubos.server.system.views.containers.VerticalContainer;
import ru.cubos.server.system.views.viewListeners.*;

import java.sql.SQLOutput;

public class ApplicationsList extends App {
    TabelContainer tabelContainer;
    ActivationListener activationListener;
    TouchTapListener touchTapListener;
    TouchDownListener touchDownListener;
    int selectedElement = -1;

    @Override
    public void onButtonPressed(char code, char buttonChar){
        super.onButtonPressed(code, buttonChar);

        Buttons.ButtonType buttonType = Buttons.getButtonType(code, buttonChar);
        if(buttonType==null) return;

        switch (buttonType){
            case ARROW_RIGHT:
                selectedElement++;
                if(selectedElement>tabelContainer.ElementsList.size()-1){selectedElement=0;}
                focus(tabelContainer.ElementsList.get(selectedElement));
                break;
            case ARROW_LEFT:
                selectedElement--;
                if(selectedElement<0)selectedElement=tabelContainer.ElementsList.size()-1;
                focus(tabelContainer.ElementsList.get(selectedElement));
                break;
            case ENTER:
                break;
        }

        return;
    }

    public ApplicationsList(Server server) {
        super(server);

        activationListener = new ActivationListener() {
            @Override
            public void activate(View view) {
                view.setBackgroundColor(new byte[]{87-128, 0-128, 112-128});

                int coords[] = ApplicationsList.this.getBaseContainer().getRenderCoordinatesInContainer(view);
                final int x = coords[0];
                final int y = coords[1];

                //System.out.println("X: " + coords[0] + ", Y: " + coords[1] + ", scrollY: " + ApplicationsList.this.getScrollY());

                if(y<0){
                    ApplicationsList.this.setScrollY(ApplicationsList.this.getScrollY() + y - 4);
                }else if(y + view.getRenderHeight()>ApplicationsList.this.getBaseContainer().getRenderHeight() + ApplicationsList.this.getBaseContainer().getRenderY() + ApplicationsList.this.getScrollY()){
                    //ApplicationsList.this.setScrollY(ApplicationsList.this.getScrollY() + y + ApplicationsList.this.getBaseContainer().getRenderHeight() - view.getRenderHeight() - 4);
                    ApplicationsList.this.setScrollY(ApplicationsList.this.getScrollY() + y - ApplicationsList.this.getBaseContainer().getRenderHeight()  + view.getRenderHeight()/2 + 20);
                }

                //System.out.println("getRenderHeight " + view.getRenderHeight());
            }

            @Override
            public void deactivate(View view) {
                view.setBackgroundColor(null);
            }
        };

        touchTapListener = new TouchTapListener() {
            @Override
            public void onTouchTap(View view, TouchTapEvent touchTapEvent) {
                System.out.println("Clicked");
            }
        };

        touchDownListener = new TouchDownListener() {
            @Override
            public void onTouchDown(View view, TouchDownEvent touchDownEvent) {
                ApplicationsList.this.focus(null);
            }
        };

        setWindowTitle("Main menu");

        getBaseContainer().setBackgroundColor(new byte[]{-96, -96, -96});

        if(server.settings.isWindowMode()) {
            setLeftOffset(30);
            setRightOffset(30);
            setTopOffset(80);
            setBottomOffset(40);
        }else{
            setLeftOffset(0);
            setRightOffset(0);
            setTopOffset(getServer().settings.getStatusBarHeight());
            setBottomOffset(0);
        }

        tabelContainer = new TabelContainer(TabelContainer.TableType.FIXED_ROWS,3);
        tabelContainer.setMargin(4);
        tabelContainer.setMarginRight(12);
        tabelContainer.setMarginLeft(6);

        addIcon("Settings" , "images//icons//apps//settings.png");
        addIcon("Network" , "images//icons//apps//network.png");
        addIcon("File browser" , "images//icons//apps//filebrowser.png");
        addIcon("Text editor" , "images//icons//apps//texteditor.png");
        addIcon("Paint" , "images//icons//apps//paint.png");
        addIcon("Server terminal" , "images//icons//apps//terminal.png");
        addIcon("Screen calibration" , "images//icons//apps//calibration.png");

        for(int i=0; i<30; i++){
            addIcon("Application " + i, "images//icons//testicon.png");
        }

        addView(tabelContainer);

        //IconView iconView = new IconView("images//icons//testicon.png");

    }

    private void addIcon(String name, String iconPath){
        DesktopIconView desktopIconView = new DesktopIconView(name , iconPath);
        desktopIconView.setActivationListener(activationListener);
        //desktopIconView.setOnTouchTapListener(this, touchTapListener);
        desktopIconView.setOnTouchTapListener(this, new TouchTapListener() {
            @Override
            public void onTouchTap(View view, TouchTapEvent touchTapEvent) {
                System.out.println("Tap on " + name);
            }
        });
        desktopIconView.setOnTouchDownListener(this, touchDownListener);

        tabelContainer.add(desktopIconView);
    }

}

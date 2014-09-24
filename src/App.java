import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Woodbin on 23.9.2014.
 * Tray Icon class
 */
public class App {
    private static App app = new App();
    private static TrayIcon icon;

    //IMAGES
    private static Image iconImage;
    private static Image loginImage;
    private static Image tasksImage;
    private static Image settingsImage;
    private static Image consoleImage;
    private static Image closeImage;
    private static Image finishImage;


    private static JPopupMenu menu;

    //REFERENCES
    private static DebugModule debugmodule = DebugModule.getInstance();
    private static Core core = Core.getInstance();
    private static DebugConsole debugConsole = new DebugConsole();



    public static App getInstance() {
        return app;
    }

    private App() {
    }


    /**
     * Initialization of tray icon
     * @param args parameters passed from main method
     */
    public static void init(String args[]){
        loadResources();
        icon = new TrayIcon(iconImage);
        icon.setToolTip("TrayWeek Alpha");
        buildTrayIconMenu();

        icon.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()==MouseEvent.BUTTON1) {
                    menu.setLocation(e.getX(),e.getY());
                    menu.setInvoker(menu);
                    menu.setVisible(true);
                }
            }
        });

        if(!SystemTray.isSupported()){
            debugmodule.debugOut("SystemTray is not supported!");

        }
        SystemTray tray = SystemTray.getSystemTray();

        try{
            tray.add(icon);
        }catch (AWTException e){
            debugmodule.debugOut("TrayIcon could not be added");
        }

    }

    /**
     * Loading resources
     */
    private static void loadResources(){
        try {

            iconImage = ImageIO.read(new File("res/calendar.png"));
            loginImage = ImageIO.read(new File("res/lock_go.png"));
            tasksImage = ImageIO.read(new File("res/note.png"));
            settingsImage = ImageIO.read(new File("res/computer.png"));
            closeImage = ImageIO.read(new File("res/door_in.png"));
            consoleImage = ImageIO.read(new File("res/console.png"));
            finishImage = ImageIO.read(new File("res/accept.png"));

        } catch (IOException e) {
            debugmodule.debugOut("Error loading icon image!");
            e.printStackTrace();
        }
    }

    /**
     * Builds tray icon JPopupMenu
     */
    private static void buildTrayIconMenu(){
        menu = new JPopupMenu();
        ActionListener popupMenuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                debugmodule.debugOut("Popup menu item ["+ e.getActionCommand()+"] was pressed.");
                if(e.getActionCommand()=="Odchod domů"){
                    String args[]=new String[1];
                    args[0] = "1";
                    core.action(CoreAction.CLOSE,args);
                }
                if(e.getActionCommand()=="Konzole"){
                    showDebugWindow();
                }
                if(e.getActionCommand()=="Login"){
                    showLoginWindow();
                }
            }
        };
        JMenuItem item;
        menu.add("TrayWeek");
        menu.addSeparator();
        menu.add(item = new JMenuItem("Login", new ImageIcon(loginImage)));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        menu.add(item = new JMenuItem("Úkoly", new ImageIcon(tasksImage)));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        menu.add(item = new JMenuItem("Dokonči úkol", new ImageIcon(finishImage)));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        item.setEnabled(false);
        menu.addSeparator();
        menu.add(item = new JMenuItem("Konzole",new ImageIcon(consoleImage)));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        menu.add(item = new JMenuItem("Nastavení",new ImageIcon(settingsImage)));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        menu.addSeparator();
        menu.add(item = new JMenuItem("Odchod domů", new ImageIcon( closeImage)));

        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);


        menu.setLabel("Menu");
        menu.setBorder(new BevelBorder(BevelBorder.RAISED));
        menu.addPopupMenuListener(new PopupPrintListener());

        menu.addMouseListener(new MousePopupListener());
    }

    private JPopupMenu getPopupMenu(){
        return menu;
    }

    private static void showDebugWindow(){
        if(!debugmodule.getConsoleWindowState()){
            debugConsole.create();
            debugmodule.setConsoleWindowState(true);
        }
        debugConsole.windowShow();
    }

    private static void showLoginWindow(){
        LoginWindow lw = new LoginWindow();
        lw.createAndShow();
    }

    public static void forwardToConsoleWindow(String message){
        debugConsole.consolePrint(message);
    }

    /**
     * Custom Mouse Popup Listener
     */
    private static class MousePopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e){
                checkPopup(e);
        }
        public void mouseClicked(MouseEvent e){
                checkPopup(e);
        }
        public void mouseReleased(MouseEvent e){
                checkPopup(e);
        }
        private void checkPopup(MouseEvent e){
            if(e.isPopupTrigger()){
                menu.show(App.getInstance().getPopupMenu(), e.getX(),e.getY());
            }

        }
    }

    /**
     * Listener for calling debug messages for JPopupMenu
     */
    private static class PopupPrintListener implements PopupMenuListener {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            debugmodule.debugOut("Popup menu will be visible!");
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            debugmodule.debugOut("Popup menu will be invisible!");
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
            debugmodule.debugOut("Popup menu is hidden!");
        }
    }
}

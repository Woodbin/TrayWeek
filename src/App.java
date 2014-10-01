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
    private static boolean projectsWindowState = false;

    //IMAGES
    private static Image iconImage;
    private static Image loginImage;
    private static Image logoutImage;
    private static Image tasksImage;
    private static Image settingsImage;
    private static Image consoleImage;
    private static Image closeImage;
    private static Image finishImage;


    private static JPopupMenu menu;

    //REFERENCES
    private static DebugModule debug = DebugModule.getInstance();
    private static Core core = Core.getInstance();
    private static DebugConsole debugConsole = new DebugConsole();
    private static ProjectsWindow projectsWindow = new ProjectsWindow();


    //JPOPUPMENU ITEM CONSTATS

    private static final int loginItem = 2;
    private static final int logoutItem = 3;
    private static final int tasksItem = 4;
    private static final int finishTaskItem = 5;

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
            debug.debugOut("SystemTray is not supported!");

        }
        SystemTray tray = SystemTray.getSystemTray();

        try{
            tray.add(icon);
        }catch (AWTException e){
            debug.debugOut("TrayIcon could not be added");
        }

    }

    /**
     * Loading resources
     */
    private static void loadResources(){
        try {

            iconImage = ImageIO.read(new File("res/calendar.png"));
            loginImage = ImageIO.read(new File("res/lock_go.png"));
            logoutImage = ImageIO.read(new File("res/lock_break.png"));
            tasksImage = ImageIO.read(new File("res/note.png"));
            settingsImage = ImageIO.read(new File("res/computer.png"));
            closeImage = ImageIO.read(new File("res/door_in.png"));
            consoleImage = ImageIO.read(new File("res/console.png"));
            finishImage = ImageIO.read(new File("res/accept.png"));

        } catch (IOException e) {
            debug.debugOut("Error loading icon image!");
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
                debug.debugOut("Popup menu item [" + e.getActionCommand() + "] was pressed.");
                if(e.getActionCommand()=="Odchod domů"){

                    try {
                        core.action(CoreAction.CLOSE);
                    } catch (CoreException e1) {
                        e1.printStackTrace();
                    }
                }
                if(e.getActionCommand()=="Konzole"){
                    showDebugWindow();
                }
                if(e.getActionCommand()=="Login"){
                    showLoginWindow();
                }
                if(e.getActionCommand()=="Úkoly"){
                    showProjectsWindow();
                }
                if(e.getActionCommand()=="Logout"){
                    try {
                        core.action(CoreAction.LOGOUT);
                    } catch (CoreException e1) {
                        e1.printStackTrace();
                    }
                }
                if(e.getActionCommand()=="Dokonči úkol"){
                    DescriptionWindow dw = new DescriptionWindow(projectsWindow.getCurrentTask().getProjectId(),true,projectsWindow);
                    dw.createAndShow();
                    try {
                        core.action(CoreAction.COMPLETETASK);
                        setTaskFinishItemState(false);
                    } catch (CoreException e1) {
                        e1.printStackTrace();
                    }
                }
                if(e.getActionCommand().equals("Nastavení")){
                    SettingsWindow sw = new SettingsWindow();
                    sw.createAndShow();
                }
            }
        };
        JMenuItem item;
        menu.add("TrayWeek");
        menu.addSeparator();
        menu.add(item = new JMenuItem("Login", new ImageIcon(loginImage)));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        menu.add(item = new JMenuItem("Logout", new ImageIcon(logoutImage)));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.setEnabled(false);
        item.addActionListener(popupMenuListener);
        menu.add(item = new JMenuItem("Úkoly", new ImageIcon(tasksImage)));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        item.setEnabled(false);
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

    /**
     * Shows console window
     */
    private static void showDebugWindow(){
        if(!debug.getConsoleWindowState()){
            debugConsole.create();
            debug.setConsoleWindowState(true);
        }
        debugConsole.windowShow();
    }

    /**
     * Shows login window
     */
    private static void showLoginWindow(){
        LoginWindow lw = new LoginWindow();
        debug.debugOut("Showing LoginWindow");
        lw.createAndShow();
    }

    /**
     * Shows ProjectsWindow
     */
    private static void showProjectsWindow(){
        if(!projectsWindowState){
            projectsWindow.create();
            projectsWindowState = true;
        }
        projectsWindow.windowShow();

    }

    /**
     * Sets TaskFinish item in PopupMenu to desired state
     * @param state
     */
    public static void setTaskFinishItemState(boolean state){
        menu.getComponent(finishTaskItem).setEnabled(state);
        debug.debugOut("finishTaskItem set to " + menu.getComponent(finishTaskItem).isEnabled());
        menu.revalidate();
    }

    /**
     * Sets Login item in PopupMenu to desired state
     * @param state
     */
    public static void setLoginItemState(boolean state){
        menu.getComponent(loginItem).setEnabled(state);
        debug.debugOut("loginItem set to " + menu.getComponent(loginItem).isEnabled());
        menu.revalidate();
    }

    /**
     * Sets Logout item in PopupMenu to desired state
     * @param state
     */
    public static void setLogoutItemState(boolean state){
        menu.getComponent(logoutItem).setEnabled(state);
        debug.debugOut("logoutItem set to " + menu.getComponent(logoutItem).isEnabled());
        menu.revalidate();
    }

    /**
     * Sets Tasks item in PopupMenu to desired state
     * @param state
     */
    public static void setTasksItemState(boolean state){
        menu.getComponent(tasksItem).setEnabled(state);
        debug.debugOut("tasksItem set to " + menu.getComponent(tasksItem).isEnabled());
        menu.revalidate();
    }

    /**
     * Forwards message to Console Window
     * @param message
     */
    public static void forwardToConsoleWindow(String message){
        debugConsole.consolePrint(message);
    }
    public static void rebuildProjectsView(){projectsWindow.refresh();}
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
            debug.debugOut("Popup menu will be visible!");
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            debug.debugOut("Popup menu will be invisible!");
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
            debug.debugOut("Popup menu is hidden!");
        }
    }
}

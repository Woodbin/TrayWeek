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
public class Application {
    private static Application app = new Application();
    private static TrayIcon icon;
    private static Image iconImg;
    private static Image loginImage;
    private static Image tasksImage;
    private static Image settingsImage;
    private static JPopupMenu menu;

    public static Application getInstance() {
        return app;
    }

    private Application() {
    }


    /**
     * Initialization of tray icon
     * @param args parameters passed from main method
     */
    private static void init(String args[]){
        loadResources();
        icon.setImage(iconImg);
        icon.setToolTip("TrayWeek Alpha");
        buildTrayIconMenu();
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()){
                    menu.setLocation(e.getX(),e.getY());
                    menu.setInvoker(menu);
                    menu.setVisible(true);
                }
            }
        });

    }

    private static void loadResources(){
        try {

            iconImg = ImageIO.read(new File("/res/calendar.png"));
            loginImage = ImageIO.read(new File("/res/lock_go.png"));
            tasksImage = ImageIO.read(new File("/res/note.png"));
            settingsImage = ImageIO.read(new File("/res/computer.png"));
        } catch (IOException e) {
            System.out.println("Error loading icon image!");
            e.printStackTrace();
        }
    }

    private static void buildTrayIconMenu(){
        menu = new JPopupMenu();
        ActionListener popupMenuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Popup menu item ["+ e.getActionCommand()+"] was pressed.");
            }
        };
        JMenuItem item;
        menu.add("TrayWeek");
        menu.add(item = new JMenuItem("Login"), new ImageIcon(loginImage));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        menu.add(item = new JMenuItem("Tasks"), new ImageIcon(tasksImage));
        item.setHorizontalTextPosition(JMenuItem.RIGHT);
        item.addActionListener(popupMenuListener);
        menu.addSeparator();
        menu.add(item = new JMenuItem("Settings"), new ImageIcon(settingsImage));
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
                menu.show(Application.getInstance().getPopupMenu(), e.getX(),e.getY());
            }
        }
    }

    private static class PopupPrintListener implements PopupMenuListener {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            System.out.println("Popup menu will be visible!");
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            System.out.println("Popup menu will be invisible!");
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
            System.out.println("Popup menu is hidden!");
        }
    }
}

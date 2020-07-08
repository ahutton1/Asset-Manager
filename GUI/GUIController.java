package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import enums.assetTypes;
import enums.employmentStatus;
import enums.statusTypes;
import enums.vendors;

public class GUIController {

    /**
     * Overarching structure
     */
    private int GUIstate;
    private JFrame window;
    private JPanel navPanel;
    private JPanel searchPanel;
    private JPanel listPanel;
    private JPanel searchListJointPanel;
    private JPanel contentPanel;

    /**
     * Navigation Panel Contents
     */
    private JButton assetsBtn;
    private JButton newAssetBtn;
    private JButton usersBtn;
    private JButton newUserBtn;
    private JButton saveBtn;        //Could potentially be unnecessary - Send to new user/asset page

    /**
     * Search Panel Contents - Assets
     */
    private JRadioButton asset_search_compName;
    private JRadioButton asset_search_assetNumber;
    private JRadioButton asset_search_assetType;
    private JRadioButton asset_search_model;
    private JRadioButton asset_search_phoneNumber;
    private ButtonGroup asset_search;

    /**
     * Search Panel Contents - Users
     */
    private JRadioButton user_search_firstName;
    private JRadioButton user_search_lastName;
    private JRadioButton user_search_empStat;
    private ButtonGroup user_search;

    /**
     * Controller for button groups
     */
    private ButtonGroup current_group;
    private JPanel button_container;

    /**
     * List Panel Contents
     */
    private JList contents;
    private JScrollPane contentScroller;

    /**
     * Content Pane Contents - Asset
     */
    private JTextArea asset_name;
    private JTextArea asset_ID;
    private JComboBox<assetTypes> asset_type;
    private JComboBox<vendors> asset_vendor;
    private JTextArea asset_model;
    private JTextArea asset_serial;
    private JComboBox<statusTypes> asset_invStat;

    /**
     * Content Pane Contents - Laptop Specific
     */
    private JComboBox<vendors> asset_laptop_carrier;
    private JTextArea asset_laptop_phoneNumber;
    private JTextArea asset_laptop_SIM;
    private JTextArea asset_laptop_IMEI;

    /**
     * Content Pane Contents - User
     */
    private JTextArea user_firstName;
    private JTextArea user_lastname;
    private JComboBox<employmentStatus> user_empStat;

    /**
     * Layouts for inner and outer planes
     */
    private FlowLayout navBarLayout;
    private FlowLayout radioButtonLayout;
    private FlowLayout overallLayout;

    /**
     * Sets the dimmensions for the different panel areas to be set to
     */
    private Dimension navDims;
    private Dimension searchButtonDims;
    private Dimension listPanelDims;

    /**
     * Testing suite used to test out the GUI without having to initialize client
     * information or the remote server.
     * @param args
     */
    public static void main(String args[]){
        GUIController guic = new GUIController();
        guic.initialize();
    }

    public void initialize(){
        window = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(screenSize.width, screenSize.height);
        overallLayout = new FlowLayout(FlowLayout.LEFT);
        window.setLayout(overallLayout);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Navigation panel initialization
        assetsBtn = new JButton("Assets");
            assetsBtn.setActionCommand("assetsBtn_viewAssetListBtn");
            assetsBtn.addActionListener(new ButtonClickListener());
        newAssetBtn = new JButton("New Asset");
            newAssetBtn.setActionCommand("newAssetBtn_createNewAssetBtn");
            newAssetBtn.addActionListener(new ButtonClickListener());
        usersBtn = new JButton("Users");
            usersBtn.setActionCommand("usersBtn_viewUserListBtn");
            usersBtn.addActionListener(new ButtonClickListener());
        newUserBtn = new JButton("New User");
            newUserBtn.setActionCommand("newUserBtn_createNewUserBtn");
            newUserBtn.addActionListener(new ButtonClickListener());
        //saveBtn = new JButton("Save");          //May not be necessary, but initialization is still included just in case
        navBarLayout = new FlowLayout(FlowLayout.LEFT);
        navPanel = new JPanel();
        navDims = new Dimension((int)screenSize.getWidth()-25,(int)screenSize.getHeight()/10);
        navPanel.setPreferredSize(navDims);
        navPanel.setLayout(navBarLayout);
        navPanel.setBackground(Color.RED);
            //navPanel.setSize((int)screenSize.getWidth(), 100);
            navPanel.add(assetsBtn);
            navPanel.add(newAssetBtn);
            navPanel.add(usersBtn);
            navPanel.add(newUserBtn);
        window.getContentPane().add(navPanel);

        //Search panel initialization
        asset_search_compName = new JRadioButton("Computer Name");
            asset_search_compName.setActionCommand("asset_search_compName_radBtn");
            asset_search_compName.addActionListener(new ButtonClickListener());
        asset_search_assetNumber = new JRadioButton("Asset Number");
            asset_search_assetNumber.setActionCommand("asset_search_assetNumber_radBtn");
            asset_search_assetNumber.addActionListener(new ButtonClickListener());
        asset_search_assetType = new JRadioButton("Asset Type");
            asset_search_assetType.setActionCommand("asset_search_assetType_radBtn");
            asset_search_assetType.addActionListener(new ButtonClickListener());
        asset_search_model = new JRadioButton("Model");
            asset_search_model.setActionCommand("asset_search_model_radBtn");
            asset_search_model.addActionListener(new ButtonClickListener());
        asset_search_phoneNumber = new JRadioButton("Phone Number");
            asset_search_phoneNumber.setActionCommand("asset_search_phoneNumber_radBtn");
            asset_search_phoneNumber.addActionListener(new ButtonClickListener());

        asset_search = new ButtonGroup();
            asset_search.add(asset_search_compName);
            asset_search.add(asset_search_assetNumber);
            asset_search.add(asset_search_assetType);
            asset_search.add(asset_search_model);
            asset_search.add(asset_search_phoneNumber);

        user_search_firstName = new JRadioButton("First Name");
            user_search_firstName.setActionCommand("user_search_firstName_radBtn");
            user_search_firstName.addActionListener(new ButtonClickListener());
        user_search_lastName = new JRadioButton("Last Name");
            user_search_lastName.setActionCommand("user_search_lastName_radBtn");
            user_search_lastName.addActionListener(new ButtonClickListener());
        user_search_empStat = new JRadioButton("Employment Status");
            user_search_empStat.setActionCommand("user_search_empStat_radBtn");
            user_search_empStat.addActionListener(new ButtonClickListener());

        user_search = new ButtonGroup();
            user_search.add(user_search_firstName);
            user_search.add(user_search_lastName);
            user_search.add(user_search_empStat);

        current_group = new ButtonGroup();
        current_group = asset_search;
        button_container = new JPanel();
        searchButtonDims = new Dimension((int)screenSize.getWidth()/3-30,(int)screenSize.getHeight()/10);
        button_container.setPreferredSize(searchButtonDims);
        radioButtonLayout = new FlowLayout(FlowLayout.LEFT);
        button_container.setLayout(radioButtonLayout);
        button_container.setBackground(Color.BLUE);
            button_container.add(asset_search_compName);
            button_container.add(asset_search_assetNumber);
            button_container.add(asset_search_assetType);
            button_container.add(asset_search_model);
            button_container.add(asset_search_phoneNumber);
            //window.getContentPane().add(button_container);

        //List panel initialization
        listPanelDims = new Dimension((int)screenSize.getWidth()/3-30, (int)screenSize.getHeight()/10*8);
        listPanel = new JPanel();
        listPanel.setBackground(Color.GREEN);
        contentScroller = new JScrollPane();
        Dimension scrollingAreaDims = new Dimension((int)screenSize.getWidth()/3-60,(int)screenSize.getHeight()/10*8-30);
        contentScroller.setPreferredSize(scrollingAreaDims);
        listPanel.setPreferredSize(listPanelDims);
        listPanel.add(contentScroller);
        //window.add(listPanel);

        /**
         * TESTING
         */
        searchListJointPanel = new JPanel();
        Dimension sljpDims = new Dimension((int)screenSize.getWidth()/3-30,(int)screenSize.getHeight()/10*9);
        searchListJointPanel.setPreferredSize(sljpDims);
        searchListJointPanel.add(button_container);
        searchListJointPanel.add(listPanel);
        window.add(searchListJointPanel);

        //Content panel initialization
        contentPanel = new JPanel();
        Dimension contentDims = new Dimension((int)screenSize.getWidth()/3*2,(int)screenSize.getHeight()/10*9);
        contentPanel.setPreferredSize(contentDims);
        contentPanel.setBackground(Color.YELLOW);
        window.add(contentPanel);

        window.setVisible(true);
    }

    /**
     * Used to switch which radio buttons are being shown on the screen
     */
    private void switchActiveSearchingGroup(String command){
        if(current_group.equals(asset_search)){
            if(command.equals("assetsBtn_viewAssetListBtn")){
                //Do nothing
            }else{
                button_container.remove(asset_search_assetNumber);
                button_container.remove(asset_search_assetType);
                button_container.remove(asset_search_compName);
                button_container.remove(asset_search_model);
                button_container.remove(asset_search_phoneNumber);

                button_container.add(user_search_firstName);
                button_container.add(user_search_lastName);
                button_container.add(user_search_empStat);

                button_container.revalidate();
                button_container.repaint();
                //window.repaint();
            }
        }else{
            if(command.equals("usersBtn_viewUserListBtn")){
                //Do nothing
            }else{
                button_container.remove(user_search_firstName);
                button_container.remove(user_search_lastName);
                button_container.remove(user_search_empStat);

                button_container.add(asset_search_compName);
                button_container.add(asset_search_assetNumber);
                button_container.add(asset_search_assetType);
                button_container.add(asset_search_model);
                button_container.add(asset_search_phoneNumber);

                button_container.revalidate();
                button_container.repaint();
                //window.repaint();
            }

        }
    }
 
    /**
     * Handles all user-initiated events that occur on the GUI
     */
    private class ButtonClickListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch(command){
                case "assetsBtn_viewAssetListBtn":
                    System.out.println("View Asset List Button Recognized");
                    switchActiveSearchingGroup(command);
                    current_group = asset_search;
                    break;
                case "newAssetBtn_createNewAssetBtn":
                    System.out.println("Create New Asset Button Recognized");
                    break;
                case "usersBtn_viewUserListBtn":
                    System.out.println("View User List Button Recognized");
                    switchActiveSearchingGroup(command);
                    current_group = user_search;
                    break;
                case "newUserBtn_createNewUserBtn":
                    System.out.println("Create New User Button Recognized");
                    break;
                case "asset_search_compName_radBtn":
                    System.out.println("Asset Computer Name Search Type Recognized");
                    break;
                case "asset_search_assetNumber_radBtn":
                    System.out.println("Asset ID Number Search Type Recognized");
                    break;
                case "asset_search_assetType_radBtn":
                    System.out.println("Asset Type Search Type Recognized");
                    break;
                case "asset_search_model_radBtn":
                    System.out.println("Asset Model Search Type Recognized");
                    break;
                case "asset_search_phoneNumber_radBtn":
                    System.out.println("Asset Phone Number Search Type Recognized");
                    break;
                case "user_search_firstName_radBtn":
                    System.out.println("User First Name Search Type Recognized");
                    break;
                case "user_search_lastName_radBtn":
                    System.out.println("User Last Name Search Type Recognized");
                    break;
                case "user_search_empStat_radBtn":
                    System.out.println("User Employment Status Search Type Recognized");
                    break;
                default:
                System.out.println(command);
                    break;
            }
        }

    }

}
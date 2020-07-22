package GUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Client.ClientDriver;
import DisplayObjects.Asset;
import DisplayObjects.User;
import DisplayObjects.sqlList;
import Server.AssetRequest;
import Server.AssetRequest.RequestType;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;

import enums.assetTypes;
import enums.employmentStatus;
import enums.statusTypes;
import enums.vendors;

public class GUIController {

    /**
     * "Neural Networking" of the program
     */
    private ClientDriver clDr;

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
    private JButton saveBtn; // Could potentially be unnecessary - Send to new user/asset page

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
    private sqlList activeList;
    private Asset activeAsset;
    private User activeUser;
    private ArrayList<Asset> activeAssetsInList;
    private ArrayList<User> activeUsersInList;
    DefaultListModel listModel = new DefaultListModel();

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
     * CONTENT INITIALIZATION AREA - POTENTIAL TEMPORARY INNER CLASS STRUCTURE USED FOR EASY-BUILD
     *      TOTAL REFERENCE DECLARATIONS . . . . . . . . 22
     *          TOTAL TEXT FIELDS  . . . . . . . . . . . 09
     *          TOTAL COMBO BOXES  . . . . . . . . . . . 05
     *          TOTAL RADIO BUTTONS  . . . . . . . . . . 02
     *          TOTAL JPANELS  . . . . . . . . . . . . . 04
     *          TOTAL GRIDLAYOUTS  . . . . . . . . . . . 02
     *  
     *      GRIDLAYOUTS  . . . . . . . . . . . . . . . . .
     *          3 ROW , 3 COLUMN . . . . . . . .  ( 3 , 3 ) 
     *          3 ROW , 1 COLUMN . . . . . . . .  ( 3 , 1 )
     */
    private GridLayout threeRoneC;
    private GridLayout threeRthreeC;

    private JPanel overall;
    private JPanel genericCells;
    private JPanel laptopCells;
    private JPanel damagedCells;

    private ButtonGroup assetDamageRepairGroup;
    private JRadioButton sentRepairTrue;
    private JRadioButton sentRepairFalse;
    private JPanel repairButtonOptionsPanel;

        //ASSETS
    private JTextField nameField;
    private JTextField IDnumberField;
    private JTextField modelField;
    private JTextField serialField;
    private JTextField phoneField;
    private JTextField imeiField;
    private JTextField simField;
    private JTextField repairDateField;
    private JTextArea damageDescriptionArea;
    
        //USERS
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField deptCodeField;

    //JCOMBO BOXES
    private JComboBox<String> assetTypeCB;
        private String[] assetTypeCB_list = {"Laptop","Desktop","Monitor","Aircard","Hotspot"};
    private JComboBox<String> assetInventoryStatusCB;
        private String[] assetInventoryStatusCB_list = {"Retired","Loaned","Disposed","Assigned","Damaged","Missing","In Stock"};
    private JComboBox<String> assetAssociatedUserCB;
        private String[] assetAssociatedUserCB_list;
    private JComboBox<String> assetLaptopAirCardCarrierCB;
        private String[] assetLaptopAirCardCarrierCB_list = {"Verizon","AT&T"};
    private JComboBox<String> assetVendorCB;
        private String[] assetVendorCB_list = {"DELL","Panasonic","Lenovo","HP","Verizon","AT&T"};

    //Archive for program use
    String updateContentPanelArchiveString = "";

    /**
     * Testing suite used to test out the GUI without having to initialize client
     * information or the remote server.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        GUIController guic = new GUIController(new ClientDriver(54312));
        guic.initialize();
    }

    public GUIController(ClientDriver clDr) {
        this.clDr = clDr;
    }

    public void initialize() {
        window = new JFrame();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(screenSize.width, screenSize.height);
        overallLayout = new FlowLayout(FlowLayout.LEFT);
        window.setLayout(overallLayout);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Navigation panel initialization
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
        // saveBtn = new JButton("Save"); //May not be necessary, but initialization is
        // still included just in case
        navBarLayout = new FlowLayout(FlowLayout.LEFT);
        navPanel = new JPanel();
        navDims = new Dimension((int) screenSize.getWidth() - 25, (int) screenSize.getHeight() / 10);
        navPanel.setPreferredSize(navDims);
        navPanel.setLayout(navBarLayout);
        navPanel.setBackground(Color.RED);
        // navPanel.setSize((int)screenSize.getWidth(), 100);
        navPanel.add(assetsBtn);
        navPanel.add(newAssetBtn);
        navPanel.add(usersBtn);
        navPanel.add(newUserBtn);
        window.getContentPane().add(navPanel);

        // Search panel initialization
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
        searchButtonDims = new Dimension((int) screenSize.getWidth() / 3 - 30, (int) screenSize.getHeight() / 10);
        button_container.setPreferredSize(searchButtonDims);
        radioButtonLayout = new FlowLayout(FlowLayout.LEFT);
        button_container.setLayout(radioButtonLayout);
        button_container.setBackground(Color.BLUE);
        button_container.add(asset_search_compName);
        button_container.add(asset_search_assetNumber);
        button_container.add(asset_search_assetType);
        button_container.add(asset_search_model);
        button_container.add(asset_search_phoneNumber);


        // LIST PANEL INITIALIZATION . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . 
        listPanelDims = new Dimension((int) screenSize.getWidth() / 3 - 30, (int) screenSize.getHeight() / 10 * 8);
        listPanel = new JPanel();
        //contents = new JList<String>(test);
        //listModel = new DefaultListModel<>();
        contents = new JList<>(listModel);
        activeAssetsInList = new ArrayList<Asset>();
        activeUsersInList = new ArrayList<User>();
        sqlList genesis = new sqlList();
        AssetRequest<sqlList> listInitializer = new AssetRequest<sqlList>(RequestType.CALL_ASSET_LIST, genesis);
        clDr.sendRequest(listInitializer);
        listPanel.setBackground(Color.GREEN);
        contentScroller = new JScrollPane(contents);
        Dimension scrollingAreaDims = new Dimension((int) screenSize.getWidth() / 3 - 60,
                (int) screenSize.getHeight() / 10 * 8 - 30);
        listPanel.setPreferredSize(listPanelDims);
        contentScroller.setPreferredSize(scrollingAreaDims);
        listPanel.add(contentScroller, BorderLayout.CENTER);
        this.contents.revalidate();
        this.contents.repaint();
        this.contentScroller.revalidate();
        this.contentScroller.repaint();
        listPanel.revalidate();
        listPanel.repaint();
        window.revalidate();
        window.repaint();

        searchListJointPanel = new JPanel();
        Dimension sljpDims = new Dimension((int) screenSize.getWidth() / 3 - 30, (int) screenSize.getHeight() / 10 * 9);
        searchListJointPanel.setPreferredSize(sljpDims);
        searchListJointPanel.add(button_container);
        searchListJointPanel.add(listPanel);
        window.add(searchListJointPanel);

        // CONTENT PANEL INITIALIZATION . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
        contentPanel = new JPanel();
        Dimension contentDims = new Dimension((int) screenSize.getWidth() / 3 * 2,
                (int) screenSize.getHeight() / 10 * 9);
        contentPanel.setPreferredSize(contentDims);
        contentPanel.setBackground(Color.YELLOW);

        threeRoneC = new GridLayout(3,1);
        threeRthreeC = new GridLayout(3,3);

        overall = new JPanel(threeRoneC);
        genericCells = new JPanel(threeRthreeC);
        laptopCells = new JPanel(threeRthreeC);
        damagedCells = new JPanel(threeRoneC);

        overall.setSize(contentDims);
        Dimension innerContentDims = new Dimension((int)contentDims.getWidth(),(int)contentDims.getHeight()/3);
        genericCells.setSize(innerContentDims);
        laptopCells.setSize(innerContentDims);
        damagedCells.setSize(innerContentDims);
        overall.add(genericCells,0,0);
        overall.add(laptopCells,0,1);
        overall.add(damagedCells,0,2);

        initializeAllComponentFields();


        window.add(contentPanel);

        window.setVisible(true);
    }

    /**
     * Initializes all of the components used for the content field
     * to help keep the main initializer less cluttered
     */
    private void initializeAllComponentFields(){
        nameField = new JTextField();
        IDnumberField = new JTextField();
        modelField = new JTextField();
        serialField = new JTextField();
        phoneField = new JTextField();
        imeiField = new JTextField();
        simField = new JTextField();
        repairDateField = new JTextField();
        damageDescriptionArea = new JTextArea();
        assetTypeCB = new JComboBox<String>(assetTypeCB_list);
        assetInventoryStatusCB = new JComboBox<String>(assetInventoryStatusCB_list);
        assetLaptopAirCardCarrierCB = new JComboBox<String>(assetLaptopAirCardCarrierCB_list);
        assetVendorCB = new JComboBox<String>(assetVendorCB_list);

        sqlList activator = new sqlList();
        AssetRequest<sqlList> request = new AssetRequest<>(AssetRequest.RequestType.CALL_USER_LIST, activator);
        clDr.sendRequest(request);

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        deptCodeField = new JTextField();

        sentRepairTrue = new JRadioButton("Asset sent for repair");
        sentRepairFalse = new JRadioButton("Asset not sent for repair");

        repairButtonOptionsPanel = new JPanel();
        repairButtonOptionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        repairButtonOptionsPanel.add(sentRepairTrue);
        repairButtonOptionsPanel.add(sentRepairFalse);
    }

    /**
     * Initializes and fills a String array listing the name of every user that
     * is in the system.
     * 
     * Information of the users' names stored in assetAssociateduserCB_list
     */
    public synchronized void fillOutAssetUserList(sqlList incomingRequest){
        //Accepting in the information
        int count = 0;
        ArrayList<String> rosetta = new ArrayList<String>();
        while(!incomingRequest.getUserList().get(count).equals(null)){
            rosetta.add(incomingRequest.getUserList().get(count).getLastName() + ", " + incomingRequest.getUserList().get(count).getFirstName());
            count++;
        }

        //Translation of the rosetta to a usable information type
        assetAssociatedUserCB_list = new String[rosetta.size()];
        for(int inc = 0; inc < rosetta.size(); inc++){
            assetAssociatedUserCB_list[inc] = rosetta.get(inc);
        }

        assetAssociatedUserCB = new JComboBox<String>(assetAssociatedUserCB_list);
    }

    /**
     * Used to switch which radio buttons are being shown on the screen
     */
    private void switchActiveSearchingGroup(String command) {
        if (current_group.equals(asset_search)) {
            if (command.equals("assetsBtn_viewAssetListBtn")) {
                // Do nothing
            } else {
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
            }
        } else {
            if (command.equals("usersBtn_viewUserListBtn")) {
                // Do nothing
            } else {
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
            }

        }
    }

    /**
     * Updates the list panel to show the information provided to this method in the
     * form of a JList
     */
    public synchronized void updateList(sqlList sqlcontents) {
        activeList = sqlcontents;
        //activeAssetsInList = null;
            for(int x = activeAssetsInList.size()-1; x > 0; x--){ activeAssetsInList.remove(x); }          //Switch to removal function
        //activeUsersInList = null;      
            for(int y = activeUsersInList.size()-1; y > 0; y--){ activeUsersInList.remove(y); }            //Switch to removal function
        contentListSelectionListener clsl = new contentListSelectionListener(this);
        this.contents.removeListSelectionListener(clsl);
            System.out.println(listModel.getSize());                                        //To remove
            System.out.println(this.listModel);                                             //To remove
        for(int i = listModel.getSize()-1; i > 0; i--){ listModel.remove(i); }
        
            System.out.println("Here");
        if(sqlcontents.getUserList().isEmpty()){
            //Asset List
            System.out.println("Update Asset List Called");
            activeAssetsInList = new ArrayList<Asset>();
            for (int i = 0; i < sqlcontents.getAssetList().size(); i++) {
                listModel.addElement((sqlcontents.getAssetList().get(i)).toListString());
                activeAssetsInList.add(i, sqlcontents.getAssetList().get(i));
            }
            activeAsset = activeAssetsInList.get(0);
        }else{
            //User list
            System.out.println("Update User List Called");
            activeUsersInList = new ArrayList<User>();
            for(int i = 0; i < sqlcontents.getUserList().size(); i++){                      //Checks out
                listModel.addElement((sqlcontents.getUserList().get(i)).toListString());    //Runs correctly (at least in terms of the GUI)
                activeUsersInList.add(i,sqlcontents.getUserList().get(i));                  
            }
            System.out.println("Recon 1");
            activeUser = activeUsersInList.get(0);
        }
        //contents.setSelectedIndex(0);
        System.out.println("Recon 2");
        if(!listModel.get(0).equals(null)){
            System.out.println("Recon 4");
            System.out.println(listModel.get(0));
            listModel.remove(0);
            System.out.println(listModel.get(0));
        }
        System.out.println("Recon 3");
        this.contents.addListSelectionListener(clsl);
        this.contents.setSelectedIndex(0);
        //this.contents.addListSelectionListener(clsl);
    }

    /**
     * Updates the content panel to show whatever asset or user is active
     */
    public void updateContentPanel(String type){
        //Removing the old
        switch(updateContentPanelArchiveString){
            case "Asset":
                genericCells.remove(nameField);
                genericCells.remove(IDnumberField);
                genericCells.remove(modelField);
                genericCells.remove(serialField);
                break;
            case "User":
                genericCells.remove(firstNameField);
                genericCells.remove(lastNameField);
                genericCells.remove(deptCodeField);
                break;
            default:
                //First time in use
                break;
        }

        //Set current type string as the old one for future removal
        updateContentPanelArchiveString = type;

        //Adding all of the necessary pieces after the removal of old information
        if(type.equals("Asset")){                   //TODO Associate the Combo Boxes and fill the user combo box list
            //An asset is to be shown to the screen

            //Declarations
            nameField.setText(activeAsset.getAssetName());
            IDnumberField.setText("" + activeAsset.getAssetNumber());
            modelField.setText(activeAsset.getAssetModel());
            serialField.setText(activeAsset.getSerialNumber());
            phoneField.setText(activeAsset.getPhoneNumber());
            imeiField.setText(activeAsset.getIMEINumber());
            simField.setText(activeAsset.getSIMNumber());

            //Placing the text fields for -GENERIC CELLS-
            genericCells.add(nameField,0,0);                            /* GENERIC CELL REGION FOR ASSETS */
            genericCells.add(IDnumberField,0,1);                        // Asset Name       | Number    | Type
                assetTypeCB.setEditable(true);

                //assetTypeCB.addActionListener();
            genericCells.add(assetTypeCB,0,2);                          // -----------------|-----------|------------
                assetInventoryStatusCB.setEditable(true);
            genericCells.add(assetInventoryStatusCB,1,0);               // Inventory Status | User      |
                assetAssociatedUserCB.setEditable(true);
            genericCells.add(assetAssociatedUserCB,1,1);                // -----------------|-----------|------------
                assetVendorCB.setEditable(true);
            genericCells.add(assetVendorCB,2,0);                        // Vendor           | Model     | Serial #
            genericCells.add(modelField,2,1);                           
            genericCells.add(serialField,2,2);

            //Placing the text fields for -LAPTOP CONDITIONS-
                assetLaptopAirCardCarrierCB.setEditable(true);
            laptopCells.add(assetLaptopAirCardCarrierCB,0,0);
            laptopCells.add(phoneField,1,0);
            laptopCells.add(imeiField,1,1);
            laptopCells.add(simField,1,2);

            //Placing the text fields for -DAMAGE CONDITIONS-
            damagedCells.add(repairButtonOptionsPanel,0,0);
            damagedCells.add(repairDateField);
            damagedCells.add(damageDescriptionArea);

        }else{
            //A user is to be shown to the screen

            //Declarations
            firstNameField.setText(activeUser.getFirstName());
            lastNameField.setText(activeUser.getLastName());
            deptCodeField.setText(activeUser.getDeptCode());

            //Placing the text fields
            genericCells.add(firstNameField,0,0);
            genericCells.add(lastNameField,0,1);
            genericCells.add(deptCodeField,0,2);

        }
    }

    /**
     * Handles all user-initiated events that occur on the GUI
     */
    private class ButtonClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "assetsBtn_viewAssetListBtn":
                    System.out.println("View Asset List Button Recognized");
                    switchActiveSearchingGroup(command);
                    current_group = asset_search;
                    sqlList sqllist = new sqlList();
                    sqllist = setActiveListType(sqllist);
                    // TODO set the search term
                    AssetRequest<sqlList> request = new AssetRequest<>(AssetRequest.RequestType.CALL_ASSET_LIST, sqllist);
                    clDr.sendRequest(request);
                    break;
                case "newAssetBtn_createNewAssetBtn":
                    System.out.println("Create New Asset Button Recognized");
                    break;
                case "usersBtn_viewUserListBtn":
                    System.out.println("View User List Button Recognized");
                    switchActiveSearchingGroup(command);
                    current_group = user_search;
                    sqlList sqllist_user = new sqlList();
                    sqllist_user = setActiveListType(sqllist_user);
                    //TODO set the search term
                    AssetRequest<sqlList> uListReq = new AssetRequest<>(AssetRequest.RequestType.CALL_USER_LIST, sqllist_user);
                    System.out.println("Sending the request to the client driver");
                    clDr.sendRequest(uListReq);
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

    /**
     * Event handler that allows the program to make changes to the searching mechanics 
     */
    private class contentListSelectionListener implements ListSelectionListener {

        GUIController source;

        public contentListSelectionListener(GUIController source){
            this.source = source;
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            System.out.println("valueChanged Registered");
            if(activeAssetsInList==null){
                //Users list is active
                System.out.println("vC User List status . . . ACTIVE");
                source.activeUser = source.activeUsersInList.get(source.contents.getSelectedIndex());
                source.updateContentPanel("User");
            }else{
                if(activeUsersInList==null){
                    System.out.println("Error in reading the lists while trying to throw the content frame");
                }else{
                    //Assets list is active
                    System.out.println("vC Asset List status . . . ACTIVE");
                    System.out.println(source.contents.getSelectedIndex());
                    source.contents.setSelectedIndex(0);
                    source.activeAsset = source.activeAssetsInList.get(source.contents.getSelectedIndex());
                    source.updateContentPanel("Asset");
                }
            }
            
        }

    }

    /**
     * Sets the active search type of the SQL List to whichever of the radio buttons is selected at that
     * given time.
     * @param sqllist
     * @return
     */
    private sqlList setActiveListType(sqlList sqllist){
        String activeButton = "";
        for(Enumeration<AbstractButton> buttons = current_group.getElements(); buttons.hasMoreElements();){
            AbstractButton button = buttons.nextElement();
            if(button.isSelected()){
                activeButton = button.getText();
            }
        }

        System.out.println(activeButton);
        switch(activeButton){
            case "Computer Name":
                sqllist.searchTypeOne = sqlList.searchType.ASSET_NAME;
                break;
            case "Asset Number":
                sqllist.searchTypeOne = sqlList.searchType.ASSET_ID;
                break;
            case "Asset Type":
                sqllist.searchTypeOne = sqlList.searchType.ASSET_TYPE;
                break;
            case "Model":
                sqllist.searchTypeOne = sqlList.searchType.ASSET_MODEL;
                break;
            case "Phone Number":
                sqllist.searchTypeOne = sqlList.searchType.ASSET_PHONE_NUMBER;
                break;
            case "First Name":
                sqllist.searchTypeOne = sqlList.searchType.USER_FIRST;
                break;
            case "Last Name":
                sqllist.searchTypeOne = sqlList.searchType.USER_LAST;
                break;
            case "Employment Status":
                sqllist.searchTypeOne = sqlList.searchType.USER_STAT;
                break;
            case "":
                System.out.println("Basic list setting set");
                sqllist.searchTypeOne = sqlList.searchType.BASIC;
                break;
            default:
                System.out.println("Error in how the system read which button is active for the list");
                break;
        }

        return sqllist;
    }


}
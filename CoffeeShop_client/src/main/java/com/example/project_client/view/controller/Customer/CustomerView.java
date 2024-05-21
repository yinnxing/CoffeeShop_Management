package com.example.project_client.view.controller.Customer;

import com.example.project_client.model.Customer;
import com.example.project_client.repository.CustomerRepository;
import com.example.project_client.router.Pages;
import com.example.project_client.router.Router;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.material2.Material2MZ;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerView implements Initializable {


    @FXML
    private TableColumn<Customer, String> col_dob;

    @FXML
    private TableColumn<Customer, Boolean> col_edit;

    @FXML
    private TableColumn<Customer, String> col_name;

    @FXML
    private TableColumn<Customer, Integer> col_ID;

    @FXML
    private TableColumn<Customer, String> col_phoneNumber;
    @FXML
    private TableColumn<Customer, Boolean> col_delete;
    @FXML
    private TableColumn<Customer, Boolean> col_read;

    @FXML
    private TableColumn<Customer, Integer> col_total;





    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchBtn;
    private CustomerRepository customerRepository = new CustomerRepository();

    private ObservableList<Customer> customersList = FXCollections.observableArrayList();
  //  Customer customer = new Customer();



    @FXML
    void HandleBack(ActionEvent event) throws IOException {
    Router.goTo(Pages.ADMIN_VIEW);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            System.out.println("load");
            loadCustomerData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeColumns();
        Router.setData(Pages.CUSTOMER_VIEW, this);


    }

    private void initializeColumns() {
        col_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_edit.setCellFactory(createEditButtonCellFactory());
        col_delete.setCellFactory(createDeleteButtonCellFactory());
        col_read.setCellFactory(createReadButtonCellFactory());
    }
    private Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>> createEditButtonCellFactory() {
        return new Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>>() {
            @Override
            public TableCell<Customer, Boolean> call(final TableColumn<Customer, Boolean> param) {
                return new TableCell<Customer, Boolean>() {
                    private final Button editButton = new Button(null, new FontIcon(Material2AL.EDIT));
                    {
                        editButton.setOnMouseClicked(event -> {
                            Customer selectedCustomer = getTableView().getItems().get(getIndex());

                            try {
                                handleEditAction(selectedCustomer);

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }


                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                        }
                    }
                };
            }
        };
    }
    private Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>> createDeleteButtonCellFactory() {
        return new Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>>() {
            @Override
            public TableCell<Customer, Boolean> call(final TableColumn<Customer, Boolean> param) {
                return new TableCell<Customer, Boolean>() {
                    private final Button deleteButton = new Button(null, new FontIcon(Material2AL.DELETE));// You can use any control here
                    {
                        deleteButton.setOnMouseClicked(event -> {
                            Customer selectedCustomer = getTableView().getItems().get(getIndex());

                            try {

                                handleDeleteAction(selectedCustomer);
                                loadCustomerData();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }


                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        };
    }
    private Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>> createReadButtonCellFactory() {
        return new Callback<TableColumn<Customer, Boolean>, TableCell<Customer, Boolean>>() {
            @Override
            public TableCell<Customer, Boolean> call(final TableColumn<Customer, Boolean> param) {
                return new TableCell<Customer, Boolean>() {
                    private final Button readButton = new Button(null, new FontIcon(Material2MZ.REMOVE_RED_EYE));// You can use any control here
                    {
                        readButton.setOnMouseClicked(event -> {
                            Customer customer = getTableView().getItems().get(getIndex());

                            try {
                                handleReadAction(customer);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }


                    @Override
                    public void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(readButton);
                        }
                    }
                };
            }
        };
    }

 private void handleEditAction(Customer selectedCustomer) throws IOException {
     UpdateCustomerView.setSelectCustomer(selectedCustomer);
     Router.showDialog(Pages.UPDATE_CUSTOMER_VIEW);
 }
    private void handleReadAction(Customer customer) throws IOException {
        ReadCustomerView.setCustomer(customer);
        Router.showDialog(Pages.READ_CUSTOMER_VIEW);
    }

    private void handleDeleteAction(Customer customer) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setContentText("Bạn có chắc chắn muốn xóa khách hàng này không?");
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            customerRepository.deleteCustomer(Long.valueOf(customer.getId()));
        }
    }
    @FXML
    private void searchCustomer() throws IOException {
        String query = searchField.getText().toLowerCase();
        ObservableList<Customer> filteredList = FXCollections.observableArrayList();

        for (Customer customer : customersList) {
            if (customer.getName().toLowerCase().contains(query) ||
                    customer.getPhoneNumber().toLowerCase().contains(query) ||
                    String.valueOf(customer.getId()).contains(query)) {
                filteredList.add(customer);
            }
        }
        initializeColumns();
        customerTable.setItems(filteredList);

    }
    public void loadCustomerData() throws IOException {
        List<Customer> lists = customerRepository.getCustomersApi();
        if (lists != null) {
            customersList = FXCollections.observableArrayList(lists);
            customerTable.setItems(customersList);
        } else {
            System.out.println("Error loading customer data: List is null");
        }
    }


    @FXML
    void HandleTableSelected(MouseEvent event) {

    }


    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        Router.showDialog(Pages.ADD_CUSTOMER_VIEW);
    }


}
package seedu.loyaltylift.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.loyaltylift.commons.core.LogsCenter;
import seedu.loyaltylift.model.order.Order;

/**
 * Panel containing the list of orders.
 */
public class OrderListPanel extends UiPart<Region> {
    private static final String FXML = "OrderListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(OrderListPanel.class);

    private final OrderInfoOnClickHandler handler;

    @FXML
    private ListView<Order> orderListView;

    /**
     * Creates a {@code OrderListPanel} with the given {@code ObservableList}.
     */
    public OrderListPanel(ObservableList<Order> orderList, OrderInfoOnClickHandler handler) {
        super(FXML);
        this.handler = handler;

        orderListView.setItems(orderList);
        orderListView.setCellFactory(listView -> new OrderListViewCell());

        orderListView.getSelectionModel().selectedItemProperty().addListener(e -> {
            Order order = orderListView.getSelectionModel().getSelectedItem();
            handler.orderInfoOnClick(order);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Order} using a {@code OrderCard}.
     */
    class OrderListViewCell extends ListCell<Order> {
        @Override
        protected void updateItem(Order order, boolean empty) {
            super.updateItem(order, empty);

            if (empty || order == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OrderCard(order, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Represents a function that handles the event of when an order card is clicked in this list.
     */
    @FunctionalInterface
    public interface OrderInfoOnClickHandler {
        /**
         * Handles the displaying of order information on main window.
         */
        void orderInfoOnClick(Order order);
    }
}

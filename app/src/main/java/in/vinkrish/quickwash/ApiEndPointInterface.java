package in.vinkrish.quickwash;

import in.vinkrish.quickwash.data.Order;
import in.vinkrish.quickwash.data.OrderResponse;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by vinkrish on 05/12/15.
 */
public interface ApiEndPointInterface {

    @POST("/order/new/index.php")
    void saveOrder (@Body Order order, Callback<OrderResponse> callBack);

    @POST("/order/new/index.php")
    Call<OrderResponse> saveNewOrder (@Body Order order);

}

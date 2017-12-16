package in.vinkrish.quickwash;

import in.vinkrish.quickwash.data.Order;
import in.vinkrish.quickwash.data.OrderResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by vinkrish on 05/12/15.
 */
public interface ApiEndPointInterface {

    @POST("/quick_wash/index.php")
    Call<OrderResponse> saveNewOrder (@Body Order order);

}

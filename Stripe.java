package com.turing.payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.turing.exception.DAOException;
import com.turing.exception.ErrorObject;
import com.turing.order.OrderDao;
import com.turing.order.OrderShortDetails;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

/**
 * Servlet implementation class Stripe
 */
@WebServlet("/stripe/*")
public class Stripe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger
			.getLogger(com.turing.payment.Stripe.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Stripe() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] service = request.getRequestURI().split("/");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String stripeToken, email, description, currency, input;
		Double amount;
		int order_id;
		JsonParser parser;
		JsonElement element;
		JsonObject jsonObject;
		Map<String, Object> params = new HashMap<String, Object>();
		ErrorObject err = null;
		Gson gson = new Gson();

		try {
			if (service.length == 4) {
				if ((input = br.readLine()) != null) {
					parser = new JsonParser();
					element = parser.parse(input);
					jsonObject = element.getAsJsonObject();
					stripeToken = jsonObject.get("stripeToken").getAsString();
					System.out.println(stripeToken);
					order_id = jsonObject.get("order_id").getAsInt();
					email = jsonObject.get("email").getAsString();
					OrderShortDetails items = new OrderDao()
							.getOrderShortDetails(order_id);
					if (items != null) {
						amount = Double.parseDouble(items.getTotal_amount());
						com.stripe.Stripe.apiKey = getServletContext()
								.getInitParameter("stripeSecretKey");
						params.put("amount", amount); // or get from request
						params.put("currency", "usd"); // or get from request
						params.put("stripeToken", stripeToken);// or get from
						params.put("source", stripeToken);										// request
						params.put("receipt_email", email);
						params.put("order_id", order_id);
						Charge charge = new Charge();
						charge = Charge.create(params);
						String status = charge.getStatus();
						System.out.println(status);
						response.getWriter().append(gson.toJson(charge));
						response.flushBuffer();
					}
				}
			}

		} catch (NumberFormatException e) {
			err = new ErrorObject(400, "STR_01", "order_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		} catch (InvalidRequestException e) {
			err = new ErrorObject(400, "STR_01", e.getMessage());
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		} catch (AuthenticationException e) {
			err = new ErrorObject(400, "STR_01", e.getMessage());
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			response.flushBuffer();
		} catch (StripeException e) {
			// TODO Auto-generated catch block
			err = new ErrorObject(400, "STR_01", e.getMessage());
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			response.flushBuffer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			err = new ErrorObject(400, "STR_01", e.getMessage());
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			response.flushBuffer();
		}
	}
}

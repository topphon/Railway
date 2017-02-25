package controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.*;

@WebServlet("/booking")
public class BookingController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = (String) request.getParameter("action");
		request.setAttribute("Auth", "booking");
		//�֧���ö�����������͡
		if (action.equals("pre")) {
			String rid = (String) request.getParameter("rid");
			String sta = new String(request.getParameter("station").getBytes("ISO8859_1"), "utf-8");
			CarDB db = new CarDB();
			ArrayList<Car> a = new ArrayList<Car>();
			a = db.getCarListByRID(rid);
			request.setAttribute("car", a);
			request.setAttribute("station", sta);
			RequestDispatcher view = request.getRequestDispatcher("preBooking.jsp");
			view.forward(request, response);
		//Insert Booking ���  Ticket
		} else if (action.equals("insert")) {
			String seat = request.getParameter("selected");//�����
			String carNo = request.getParameter("carno");//�����Ţ���
			String time = request.getParameter("datebook");//�ѹ����͡�Թ�ҧ
			String d[] = seat.split(",");
			System.out.println(time);
			HttpSession sess = request.getSession();
			BookingDB bdb = new BookingDB();
			//ત�����  user ���� admin ����� user ���繡�èͧ ����� admin ���繡�ë���
			if (sess.getAttribute("type").equals("user")) {
				bdb.addBooking(time, "0", sess.getAttribute("uid").toString());
			} else {
				bdb.addBooking(time, "1", sess.getAttribute("uid").toString());
			}
			int lns = bdb.getLastInsert().getBookingID();//insert Booking
			sess.setAttribute("BookingID", bdb.getLastInsert().getBookingID());
			TicketDB tdb = new TicketDB();//Insert Ticket
			for (int i = 0; i < d.length; i++) {
				System.out.println(lns);
				tdb.addTicket(carNo, d[i], lns);
			}
			response.sendRedirect("payin");
			//�繡���觵�ͨҡ˹�� PreBooking ��ѧ˹�����͡�����
		} else if (action.equals("book")) {
			String cid = request.getParameter("cid");
			String date = request.getParameter("dategg");
			String carType = request.getParameter("cartype");
			BookingDB bdb = new BookingDB();
			String t = bdb.getAllSeatByDate(date, cid);
			request.setAttribute("date", date);
			request.setAttribute("carno", cid);
			request.setAttribute("ticket", t);
			request.setAttribute("cartype", carType);
			RequestDispatcher view = request.getRequestDispatcher("booking.jsp");
			view.forward(request, response);
		} else {
			response.sendRedirect("main");
		}
	}
}

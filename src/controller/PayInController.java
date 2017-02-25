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
import model.BookingDetail;
import model.BookingDetailDB;
import model.TicketDetail;
import model.TicketDetailDB;

@WebServlet("/payin")
public class PayInController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		//�ó�������������� ���繡�ô֧ BookingID �ҡ Session ���;����㺨����Թ/����
		if (action == null) {
			HttpSession sess = request.getSession();
			String BookingId = sess.getAttribute("BookingID").toString();
			System.out.println(BookingId);
			BookingDetailDB bookDB = new BookingDetailDB();
			BookingDetail b = new BookingDetail();
			b = bookDB.getBookingDetail(BookingId);
			request.setAttribute("blist", b);
			TicketDetailDB ticketDB = new TicketDetailDB();
			ArrayList<TicketDetail> tic = new ArrayList<TicketDetail>();
			tic = ticketDB.getTicketByBookingID(Integer.parseInt(BookingId));
			request.setAttribute("t", tic);
			request.setAttribute("tlist", tic);//����¡�õ�������͹�ʴ�
			RequestDispatcher view = request.getRequestDispatcher("payin.jsp");
			view.forward(request, response);
		//�óշ���ҷ��ʧ������� Null �� get BookingID 
		} else {
			String BookingId = request.getParameter("booking");
			BookingDetailDB bookDB = new BookingDetailDB();
			BookingDetail b = new BookingDetail();
			b = bookDB.getBookingDetail(BookingId);
			request.setAttribute("blist", b);
			TicketDetailDB ticketDB = new TicketDetailDB();
			ArrayList<TicketDetail> tic = new ArrayList<TicketDetail>();
			tic = ticketDB.getTicketByBookingID(Integer.parseInt(BookingId));
			request.setAttribute("t", tic);
			request.setAttribute("tlist", tic);
			RequestDispatcher view = request.getRequestDispatcher("payin.jsp");
			view.forward(request, response);
		}
	}
}

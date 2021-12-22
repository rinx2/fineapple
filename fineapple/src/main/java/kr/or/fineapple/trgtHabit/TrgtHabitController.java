package kr.or.fineapple.trgtHabit;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.or.fineapple.domain.TrgtHabit;
import kr.or.fineapple.domain.User;
import kr.or.fineapple.service.trgtHabit.TrgtHabitService;

@Controller
@RequestMapping("/trgtHabit/*")
public class TrgtHabitController {
	
	@Autowired
	@Qualifier("trgtHabitServiceImpl")
	private TrgtHabitService trgtHabitService;
	
	public TrgtHabitController() {
		System.out.println(this.getClass());
	}

	@RequestMapping(value="getTrgtHabit", method=RequestMethod.GET)
	public ModelAndView getTrgtHabit(HttpSession session) {
	
		System.out.println("/trgtHabit/getTrgtHabit : GET");
		
		////����ڿ��� �������� ���� ù ȭ�鿡 �ʿ��� ���� ��ȸ(��ǥ �� Ŀ��, ���� ���м��뷮 ����)
		String userId = ((User)session.getAttribute("user")).getUserId();
		LocalDate date = LocalDate.now();
		int trgtCateNo = 1;
		TrgtHabit trgtHabit = trgtHabitService.getTrgtHabit(userId, date, trgtCateNo);
		double userWtrIntake = trgtHabitService.getWtrIntake(userId, date);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("trgtHabit", trgtHabit);
		mav.addObject("userWtrIntake", userWtrIntake);
		mav.setViewName("trgtHabit/getTrgtHabit.html");
		
		return mav;
	}
	
	@RequestMapping(value="addTrgtHabit", method=RequestMethod.POST)
	public ModelAndView addTrgtHabit(@ModelAttribute TrgtHabit trgtHabit) {
		
		System.out.println("/trgtHabit/addTrgtHabit : POST");

		trgtHabitService.addTrgtHabit(trgtHabit.getUserId(), trgtHabit);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("trgtHabit", trgtHabit);
		mav.addObject("date", LocalDate.now());
		mav.setViewName("redirect:/trgtHabit/json/getTrgtHabit");
		
		return mav;
	}
	

}
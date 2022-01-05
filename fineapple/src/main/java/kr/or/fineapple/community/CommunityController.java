package kr.or.fineapple.community;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kr.or.fineapple.domain.User;
import kr.or.fineapple.domain.community.Board;
import kr.or.fineapple.domain.community.Group;
import kr.or.fineapple.domain.community.GroupUser;
import kr.or.fineapple.domain.community.Report;
import kr.or.fineapple.service.community.CommunityService;
import oracle.net.aso.m;

@Controller
@RequestMapping("/community/*")
public class CommunityController {
	
	public CommunityController() {
		System.out.println(getClass().getName() + "생성함");
	}
	
	@Autowired
	@Qualifier("communityServiceImpl")
	private CommunityService communityService;
	
	@GetMapping(value = "getPost")
	public String getViewTest(@ModelAttribute("board") Board board, Model model, HttpServletRequest request) {
		
		User usersys = (User)request.getSession(true).getAttribute("user");
		
		System.out.println(usersys);
		
		board.setUser((User)request.getSession().getAttribute("user"));
		
		Map map =  communityService.getPost(board);
		
		System.out.println(map.get("board")+"Scope에 담기전 Board");
		
		model.addAttribute("map", map);
		 
		return "community/getPost.html";
	}
	
	//@RequestMapping(value = "getBoard", method = RequestMethod.GET)
	@GetMapping(value = "getBoard")
	public String getPostList(Model model) {
		
		List<Board> list = communityService.getPostList();
		
		model.addAttribute("list", list);
		
		return "community/getBoard.html";
		
	}
	
	@GetMapping(value = "addPostView")
	public String addPostView() {
		return "community/addPost.html";
	}
	

	@PostMapping(value = "addPost")
	public String addPost(@ModelAttribute Board board, @ModelAttribute Group group, HttpServletRequest request) {
		
		
		
		board.setUser((User)request.getSession().getAttribute("user"));
			
		communityService.addPost(board);
		
		return "redirect:/community/getBoard";
	}
	
	@RequestMapping(value = "getMyGroupList", method = RequestMethod.GET)
	public ModelAndView getMyGroupList() {
		
			GroupUser groupUser = new GroupUser();
			User user = new User();
			user.setUserId("bbb123@gmil.com");
			groupUser.setUser(user);
			groupUser.setGroupStt(2);
			
		List<Group> list =   communityService.getGroupInterGroup(groupUser);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("community/getMyGroupList.html");
		modelAndView.addObject("list", list);
		return modelAndView;
	}
	
	@GetMapping(value = "addGroupView")
	public String addGroupView() {
		return "community/addGroupView.html";
	}
	
	@GetMapping(value = "addReportView")
	public String addReportView() {
		return null;
	}
	
	
	
	
	
	@PostMapping(value = "addGroup")
	public String addGroup(@ModelAttribute("group") Group group) {
		communityService.addGroup(group);
		
		return "community/addGroup.html";
		
	}
	
	@GetMapping(value="getAlarmList")
	public List getAlarmList() {

		return communityService.getAlarmList();
	}
	
	
//	@RequestMapping(value="reportPostView", method = RequestMethod.POST)
//	public String Test(@RequestBody String str) {
//		System.out.println(str);
//		System.out.println("ddddddddddddddddd");
//		return null;
//	}
	
	@RequestMapping(value="reportPostView", method = RequestMethod.POST)
	public String report(@RequestBody String str, Model model) {
		String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy MM dd HH mm"));
		
		model.addAttribute("time", time);
		
		return "community/addReportView :: reportPostView";
	}
	
	
	@RequestMapping(value = "addBattleView", method = RequestMethod.POST)
	public String addBattleView(Model model) {
		
		System.out.println("addBattleView 거침");
		
		return "community/addBattleView :: addBattleView";
	}

	
	
	
	
	
	
}


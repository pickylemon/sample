package com.portfolio.www.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.www.auth.dto.MemberDto;
import com.portfolio.www.auth.message.AuthMessageEnum;
import com.portfolio.www.auth.service.JoinService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class JoinController {
	private final JoinService joinService;
	

	@GetMapping("/auth/joinPage.do")
	public ModelAndView join() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auth/join");
		
		return mv;
	}
	
	/**
	 * 사용자 입력을 가지고 회원가입을 처리하는 메서드
	 * @param memberDto
	 * @param model
	 * @param rattr
	 * @return
	 */
	
	//TODO 사용자 입력 validation 추가하기 
	@PostMapping("/auth/joinPage.do")
	public ModelAndView join(@ModelAttribute @Validated MemberDto memberDto, BindingResult result, HttpServletRequest request, Model model, RedirectAttributes rattr ) {
		log.info("memberDto={}", memberDto);
		ModelAndView mv = new ModelAndView();
		String contextPath = request.getContextPath();
		
		if(result.hasErrors()) {
			//검증에 실패하면 해당 메시지를 가지고 다시 입력페이지로 이동.
			result.getAllErrors().forEach(System.out::println);
//			model.addAllAttributes(result.getModel());
			model.addAttribute("code", AuthMessageEnum.JOIN_FAIL.getCode());
			model.addAttribute("msg", AuthMessageEnum.JOIN_FAIL.getMsg());
			model.addAttribute("memberDto",memberDto);
			mv.setViewName("auth/join"); 
			return mv;
		}
		//에러가 발생해 다시 회원가입 화면 뷰를 보여줄 때, 사용자 입력을 그대로 전달하기 위해
		//memberDto객체를 만들어 둠. 그냥 memberDto를 모델에 담아 전달하면 
		//암호화된 값이 전달되므로 의도에 맞지 않는다.
		MemberDto beforeEncDto = new MemberDto(memberDto);
		
		int code = joinService.join(memberDto, contextPath);
		
		if(code == 1) {
			//회원가입 성공시 다시 홈으로 이동
			//Enum자체를 model이나 flashMap에 담을 수는 없는걸까. 뭔가 view에서 못받던데
			rattr.addFlashAttribute("code", AuthMessageEnum.SUCCESS.getCode());
			rattr.addFlashAttribute("msg", AuthMessageEnum.SUCCESS.getMsg());
			
			//QUESTION redirect:/ 라고 하면 redirect는 되는데, flashAttribute가 전달 안됨.
			//tilesViewResolver가 우선순위 1이라서 그런가?
			mv.setViewName("redirect:/index.do"); 
			return mv;
		} else if (code == 0){
			model.addAttribute("code", AuthMessageEnum.ALREADY_EXISTS.getCode());
			model.addAttribute("msg", AuthMessageEnum.ALREADY_EXISTS.getMsg());
		} else { //code == -1
			model.addAttribute("code", AuthMessageEnum.JOIN_FAIL.getCode());
			model.addAttribute("msg", AuthMessageEnum.JOIN_FAIL.getMsg());
		}
		//회원가입 실패시 유저의 입력데이터 그대로 들고 다시 form으로
		model.addAttribute("memberDto",beforeEncDto);
		mv.setViewName("auth/join"); 
		return mv;
		
	}
	
	//인증메일 확인하기
	@GetMapping("/emailAuth.do")
	public String emailAuth(@RequestParam("uri") String uri, RedirectAttributes rattr) {
		log.info("uri={}", uri);
		
		//1. uri가 유효한지 확인하기(memberAuth에 해당 uri가 있는지)
		int code = joinService.emailAuth(uri);
		// Service 내부 로직에서 처리하기
		//    -> 없다? 에러 : 유효하지 않은 주소입니다.
		//    -> 있으면 
		//	  -> 2. 유효시간 이내인지?
		//		-> 시간 지남 : 메일 재발송
		//      -> 시간 유효하면
		//		-> 3. memberAuth의 해당 uri로 식별되는 memberSeq가 member테이블에도 있는지
		//        -> 없다? 에러
		//        -> 있다? member와 memberAuth에 인증 "Y"로 update치기
		
		//성공시 다시 홈으로
		if(code == 1) {
			rattr.addFlashAttribute("code", AuthMessageEnum.AUTH_MAIL_SUCCESS.getCode());
			rattr.addFlashAttribute("msg", AuthMessageEnum.AUTH_MAIL_SUCCESS.getMsg());
			//성공시에는 로그인 페이지로 이동하는게 더 낫나..?
		} else {
			rattr.addFlashAttribute("code", AuthMessageEnum.AUTH_MAIL_FAIL.getCode());
			rattr.addFlashAttribute("msg", AuthMessageEnum.AUTH_MAIL_FAIL.getMsg());
		} 
		return "redirect:/index.do";
	}
}

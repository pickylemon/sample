package com.portfolio.www.auth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.www.auth.message.LoginMessageEnum;
import com.portfolio.www.auth.service.LoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class LoginController {
	private final LoginService loginService;
	
	@GetMapping("/loginPage.do")
	public ModelAndView loginPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auth/login");
		
		return mv;
	}
	
	/**
	 * 유저 입력값으로 로그인 처리
	 * 성공시 - 세션에 저장 및 아이디 기억 기능 여부에 따른 쿠키 처리
	 * 실패시 - 입력값과 메시지를 가지고 로그인 페이지로 이동
	 * @param memberId
	 * @param passwd
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/loginPage.do")
	public String login(String memberId, String passwd, Model model, 
			boolean rememberMe, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rattr) {
		
		int code = loginService.login(memberId, passwd);
		
		if(code > 0) {
			//로그인 성공 : 로그인 성공시, memberSeq를 반환하도록 만들어 둠 
			int memberSeq = code;
			//1. 세션 처리(세션에 memberId보다는 seq를 저장하는 편이 더 유용한듯)
			HttpSession session = request.getSession();
			session.setAttribute("memberSeq", memberSeq);
			
			//2.rememberMe 설정에 따른 쿠키 처리
			log.info("rememberMe={}", rememberMe);
		
			//rememberMe 설정에 따른 처리
			Cookie cookie = makeCookie(memberId, rememberMe);
			response.addCookie(cookie);
			
			
			rattr.addFlashAttribute("code", LoginMessageEnum.LOGIN_SUCCESS.getCode());
			rattr.addFlashAttribute("msg", LoginMessageEnum.LOGIN_SUCCESS.getMsg());

			return "redirect:/index.do"; //로그인 성공시 홈으로 이동
			
		} else if (code == -1) {
			//비밀번호 불일치
			model.addAttribute("code", LoginMessageEnum.WRONG_PASSWD.getCode());
			model.addAttribute("msg", LoginMessageEnum.WRONG_PASSWD.getMsg());
			
		} else {
			//해당 아이디로 가입된 회원 없음 code = -9
			model.addAttribute("code", LoginMessageEnum.USER_NOT_REGISTERED.getCode());
			model.addAttribute("msg", LoginMessageEnum.USER_NOT_REGISTERED.getMsg());
		}
		//로그인 실패한 경우, 유저의 입력값을 가지고 다시 로그인 페이지로 돌아감
		model.addAttribute("memberId", memberId);
//		model.addAttribute("passwd", passwd); 비밀번호는 굳이 안 넘겨도 될 것 같다.
		return "auth/login";
	}
	
	//뷰에서, 로그인 상태에서만 로그아웃 버튼이 보이도록 지정한 상태이지만, 
	//사실 주소창에 직접 치고 들어올 수도 있다(get) 
	//-> 비정상적인 접근. 어떻게 대응을 해야할까? 404를 보여줘야 하나 아니면 그냥 조치없이 홈으로 가야하나..
	@GetMapping("/logout.do")
	public String logout(HttpServletRequest request, RedirectAttributes rattr) {
		HttpSession session = request.getSession(false);
		if(!ObjectUtils.isEmpty(session)) {
			session.invalidate();
			rattr.addFlashAttribute("code", LoginMessageEnum.LOGOUT_SUCCESS.getCode());
			rattr.addFlashAttribute("msg", LoginMessageEnum.LOGOUT_SUCCESS.getMsg());
		}
		return "redirect:/index.do";
	}

	private Cookie makeCookie(String memberId, boolean rememberMe) {
		Cookie cookie;
		if(rememberMe) { //아이디 저장 체크박스 선택시
			cookie = new Cookie("memberId", memberId);
		} else { //아이디 저장 체크박스 해제시 - 기존 쿠키 덮어버리기
			cookie = new Cookie("memberId","");
			cookie.setMaxAge(0);
		}
		return cookie;
	}
	
	@RequestMapping("/auth/resetPassword.do")
	public ModelAndView findPassword() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auth/reset-password");	
		return mv;
	}

}

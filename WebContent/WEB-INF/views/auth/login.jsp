<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
String ctx = request.getContextPath();
%>
<c:set var="savedId" value="${cookie.memberId.value}"/>



    <!--================================
            START LOGIN AREA
    =================================-->
    <section class="login_area section--padding2">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 offset-lg-3">
                    <form action="<c:url value='/auth/loginPage.do'/>" method="post">
                        <div class="cardify login">
                            <div class="login--header">
                                <h3>Welcome Back</h3>
                                <p>You can sign in with your Id</p>
                            </div>
                            <!-- end .login_header -->
                            <div class="login--form">
                                <div class="form-group">
                                    <label for="user_name">Id</label>
                                    <input id="user_name" type="text" class="text_field" placeholder="Enter your username..." name="memberId" value="${savedId ne null? savedId : memberId}">
                                </div>

                                <div class="form-group">
                                    <label for="pass">Password</label>
                                    <input id="pass" type="text" class="text_field" placeholder="Enter your password..." name="passwd">
                                </div>

                                <div class="form-group">
                                    <div class="custom_checkbox">
                                        <input type="checkbox" id="ch2" name="rememberMe" ${savedId ne null? 'checked' : '' }>
                                        <!-- 체크 박스 속성 !!-->
                                        <!-- 체크를 하면 파라미터 넘어가고, 체크를 하지 않으면 파라미터 자체가 넘어가지 않는다. (이름도 전달 안됨) -->
                                        <label for="ch2">
                                            <span class="shadow_checkbox"></span>
                                            <span class="label_text">Remember me</span>
                                        </label>
                                    </div>
                                </div>

                                <button class="btn btn--md btn--round" type="submit">Login Now</button>

                                <div class="login_assist">
                                    <p class="recover">Lost your
                                        <a href="pass-recovery.html">username</a> or
                                        <a href="<%=ctx%>/auth/resetPassword.do">password</a>?</p>
                                    <p class="signup">Don't have an
                                        <a href="<c:url value='/auth/joinPage.do'/>">account</a>?</p>
                                </div>
                            </div>
                            <!-- end .login--form -->
                        </div>
                        <!-- end .cardify -->
                    </form>
                </div>
                <!-- end .col-md-6 -->
            </div>
            <!-- end .row -->
        </div>
        <!-- end .container -->
    </section>
    <script>
    let msg = '${msg}'
    let code = '${code}'
    let idElem = document.querySelector('input[name=memberId]')
    let passwdElem = document.querySelector('input[name=passwd]')
    
    window.onload = function(){
    	showErrorMsg(msg, code)
    }
    
    function showErrorMsg(msg, code) {
    	if(code != -1 && code != -9) return;
    	
    	let errMsgElem = document.createElement("p")
    	errMsgElem.style.color = 'red'
    	
    	if(code == -9) { //아이디 없음
    		let msg = document.createTextNode("존재하지 않는 아이디입니다.");
    		errMsgElem.append(msg)
    		idElem.closest('div').append(errMsgElem)
    	} else if(code == -1) { //비밀번호 없음
    		let msg = document.createTextNode("비밀번호가 틀렸습니다.");
    		errMsgElem.append(msg)
    		passwdElem.closest('div').append(errMsgElem)
    	}
    }

    </script>
    <!--================================
            END LOGIN AREA
    =================================-->

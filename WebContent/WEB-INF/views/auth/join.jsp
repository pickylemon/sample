<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
String ctx = request.getContextPath();
%>
    <!--================================
            START SIGNUP AREA
    =================================-->
    <section class="signup_area section--padding2">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 offset-lg-3">
<%--                     <form:form action="${pageContext.request.contextPath}/auth/joinPage.do" method="post"> --%>
                    <form action="${pageContext.request.contextPath}/auth/joinPage.do" method="post" onsubmit="return pwdEquals(this)">
                        <div class="cardify signup_form">
                            <div class="login--header">
                                <h3>Create Your Account</h3>
                                <p>Please fill the following fields with appropriate information to register a new MartPlace
                                    account.
                                </p>
                            </div>
                            <!-- end .login_header -->

                            <div class="login--form">
                                <div class="form-group">
                                    <label for="urname">Your Name</label>
                                    <input id="urname" type="text" class="text_field" placeholder="Enter your Name" name="memberNm" value="${memberDto.memberNm }">
<%--                                     <form:errors value="${error.defaultMessage }"/> --%>
                            		<spring:hasBindErrors name="memberDto">
                                    <c:if test="${errors.hasFieldErrors('memberNm') }">
                                    	<strong style="color:red">${errors.getFieldError('memberNm').defaultMessage }</strong>
                                    </c:if>
                                    </spring:hasBindErrors>
                                </div>

                                <div class="form-group">
                                    <label for="email_ad">Email Address</label>
                                    <input id="email_ad" type="text" class="text_field" placeholder="Enter your email address" name="email" value="${memberDto.email }">
                                    <spring:hasBindErrors name="memberDto">
                                    <c:if test="${errors.hasFieldErrors('email') }">
                                    	<strong style="color:red">${errors.getFieldError('email').defaultMessage }</strong>
                                    </c:if>
                                    </spring:hasBindErrors>
                                </div>

                                <div class="form-group">
                                    <label for="user_name">Id</label>
                                    <input id="user_name" type="text" class="text_field" placeholder="Enter your username..." name="memberId" value="${memberDto.memberId }">
                                    <spring:hasBindErrors name="memberDto">
                                    <c:if test="${errors.hasFieldErrors('memberId') }">
                                    	<strong style="color:red">${errors.getFieldError('memberId').defaultMessage }</strong>
                                    </c:if>
                                    </spring:hasBindErrors>
                                </div>

                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input id="password" type="text" class="text_field" placeholder="Enter your password..." name="passwd" >
                                     <spring:hasBindErrors name="memberDto">
                                     <c:if test="${errors.hasFieldErrors('passwd') }">
                                    	<strong style="color:red">${errors.getFieldError('passwd').defaultMessage }</strong>
                                    </c:if>
                                    </spring:hasBindErrors>
                                </div>

                                <div class="form-group">
                                    <label for="con_pass">Confirm Password</label>
                                    <input id="con_pass" type="text" class="text_field" placeholder="Confirm password" name="passwd2">
                                </div>

                                <button class="btn btn--md btn--round register_btn" type="submit">Register Now</button>

                                <div class="login_assist">
                                    <p>Already have an account?
                                        <a href="<c:url value='/auth/loginPage.do'/>">Login</a>
                                    </p>
                                </div>
                            </div>
                        
                            <!-- end .login--form -->
                        </div>
                        <!-- end .cardify -->
<%--                     </form:form> --%>
                    </form>
                    
                </div>
                <!-- end .col-md-6 -->
            </div>
            <!-- end .row -->
        </div>
        <!-- end .container -->
    </section>
    <script>
    <!-- 비밀번호 확인 실패하면 submit 안되게 -->
    function pwdEquals(elem) { 
    	console.log(elem)
    	let pwd1 = elem.querySelector("input[name=passwd]")
    	let pwd2 = elem.querySelector("input[name=passwd2]")    	
    	    	
    	console.log(pwd1.value)
    	if(pwd1.value !== pwd2.value) {
    		if(document.querySelector('#pwdDiff') == null) {
        		const notice = createNotice()
        		pwd1.closest('div').append(notice)
    		}

    		return false;
    	}
    	
    	return true;
    	
    	function createNotice(){
    		let notice = document.createElement("p")
        	notice.style.color = 'red'
        	notice.id = 'pwdDiff'
        	let msg = document.createTextNode("비밀번호가 일치하지 않습니다.");
    		
    		notice.appendChild(msg);
    		
    		return notice
    	}
    }
    
    let code = '${code}'
    let msg = '${msg}'
    window.onload = function(){
    	if(msg!=''){
    		alert(msg)
    		console.log(code)
    	}
    }
    
    
    </script>
    <!--================================
            END SIGNUP AREA
    =================================-->
</html>
/* 회원가입 유효성 검사 규칙 */
function signupForm(){
	console.log("회원가입폼")
	/*DOM 으로 form 연결*/
	/* form은 name을 주면 ElementById 안써도 가능*/
	let form = document.signup_form;
	
	if(form.id.value === ""){
		alert("새로운 id 입력");
		/*커서를 id로 지정*/
		form.id.focus();
	}else if(form.pw.value === ""){
		alert("새로운 pw 입력");
		form.pw.focus();
	}else if(form.mail.value === ""){
		alert("새로운 mail 입력");
		form.mail.focus();
	}else if(form.phone.value === ""){
		alert("새로운 phone 입력");
		form.phone.focus();
	}else{
		/* 위 조건을 전부 만족하지 않으면 전송 */
		form.submit();
	}
}
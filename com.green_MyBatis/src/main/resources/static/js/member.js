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

// 회원이 로그인 된 상태면 글쓰기 가능, 아니면 "로그인 후 사용가능" 메시지 출력
let write = document.getElementById("writeBtn");
write.addEventListener("click",function(){
	const isLogin = this.dataset.login;
	
	if(isLogin == "true"){
		/* 로그인이 된 상태 => 글쓰기 화면으로 이동 => /board/write */
		location.href = "/board/write";
	}else{
		/* 로그인이 안된 상태 => 글쓰기 안됨, 로그인 화면으로 이동 => alert창 띄우기 */
		alert("로그인 후 사용가능");
		location.href = "/member/login";
	}
})
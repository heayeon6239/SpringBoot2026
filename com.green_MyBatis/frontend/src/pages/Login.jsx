import { useState, useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Member.css';

export default function Login() {

    const {login} = useContext(AuthContext);

    // 입력값 상태 변수
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');

    // 이동할 때 사용하는 커스텀 훅
    const navigate = useNavigate();

    // 로그인 버튼 클릭시 실행되는 핸들러 함수
    const loginHandler = () => {
        // id, pw 입력했는지 검사
        if(id === ''){
            alert("아이디 입력하세요.");
            return; // signup함수 밖으로 빠져나옴
        }
        if(pw === ''){
            alert("비밀번호를 입력하세요.");
            return;
        }

        // 서버로 로그인 요청 (JSON형태로)
        axios.post('/api/member/login',{id:id, pw:pw})
        .then((res)=>{
            // 로그인 성공했을 경우
            if(res.data){
                alert(`${res.data.id}님 환영합니다.`);
                // AuthContext login함수 호출

                login(res.data.id);
                console.log(res.data);
                navigate('/');
            }
            // 실패
            else{
                alert("로그인 실패");
                navigate('/member/signup');
            }
        })
        .catch((error)=>{
            console.log(error);
            navigate('/member/signup');
        })
    }
 

  return (
    <div id="section_wrap">
      <div className="word">로그인</div>

      <table width="500" border="1">
        <tbody>
          <tr>
            <td>아이디</td>
            <td>
              <input type="text" name="id" onChange={(e)=>setId(e.target.value)}/>
            </td>
          </tr>

          <tr>
            <td>비밀번호</td>
            <td>
              <input type="password" name="pw" onChange={(e)=>setPw(e.target.value)} />
            </td>
          </tr>

          <tr>
            <td colSpan="2" align="center">
              <button onClick={loginHandler}>로그인</button>
              <button type="reset">취소</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
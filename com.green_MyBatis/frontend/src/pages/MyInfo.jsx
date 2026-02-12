import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../contexts/AuthContext';
import { useContext } from 'react';
import './Member.css';

export default function MyInfo() {

    // 회원탈퇴를 고려
    const {logout} = useContext(AuthContext);

    // 입력값을 저장할 상태 변수
    // const [myinfo, setMyinfo] = useState({});

    const [member, setMember] = useState(null);

    // 이동
    const navigate = useNavigate();

    // 서버 연결해서 조회하기
    useEffect(()=>{
        axios.get('/api/member/myinfo')
        .then((res)=>{
            // 현재 내 정보가 없을 때
            if(!res.data){
                alert("로그인 필요");
                navigate("/member/login");
            }
            // 현재 내 정보가 있을 때
            else{
                console.log("받아온 데이터 : ",res.data);
                // setMyinfo(res.data);
                setMember(res.data);
            } 
        })
        .catch((error)=>{
            console.log(error);
        })
    },[])

    // 지금 현재 member가 null값이므로 개인정보 출력이 안됨
    if(!member){
        return <div>로딩중 ...</div>
    }

    // 회원 삭제 핸들러
    const deleteHandler = () => {
        if(!window.confirm("정말 삭제하시겠습니까?")){
            return;
        }
        
        axios.delete('/api/member/delete')
        .then((res)=>{
            if(res.data === 1){
                alert("회원이 삭제되었습니다.");
                logout(); // 로그아웃 함수 호출
                navigate("/");
            }else{
                alert("삭제 실패");
            }
        })
        .catch((error)=>console.log(error));
    }

  return (
    <section>
      <div id="section_wrap">
        <div className="word">
          <h2>개인 회원 상세 정보</h2>
        </div>

        <div className="content">
          <table border="1">
            <tbody>
              <tr>
                <th>아이디</th>
                <td>{member.id}</td>
              </tr>
              <tr>
                <th>이메일</th>
                <td>{member.mail}</td>
              </tr>
              <tr>
                <th>전화</th>
                <td>{member.phone}</td>
              </tr>
              <tr>
                <th>등록일</th>
                <td>{member.reg_date}</td>
              </tr>
            </tbody>
          </table>

          {/* 버튼 영역 */}
          <div className="btn-area" style={{ marginTop: '20px' }}>
            <button className="btn" onClick={() => navigate('/member/modify')}>
              회원수정
            </button>

            <button className="btn btn-danger" onClick={deleteHandler}>
              회원탈퇴
            </button>

            <button className="btn" onClick={() => navigate('/')}>
              홈으로
            </button>
          </div>
        </div>
      </div>
    </section>
  );
}
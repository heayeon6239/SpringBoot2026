package replyboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import replyboard.dto.ReplyBoardDTO;
import replyboard.mapper.ReplyBoardMapper;

@Service
public class ReplyBoardServiceImpl implements ReplyBoardService{
	
	@Autowired
	ReplyBoardMapper replyBoardMapper;

	@Override
	public void insertReplyBoard(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardServiceImpl insertReplyBoard()");
		replyBoardMapper.insertReplyBoard(rdto);
		
	}

	@Override
	public List<ReplyBoardDTO> getAllReplyBoard() {
		System.out.println("ReplyBoardServiceImpl getAllReplyBoard()");
		return replyBoardMapper.getAllReplyBoard();
	}

	@Override
	public ReplyBoardDTO getOneBoard(int num) {
		System.out.println("ReplyBoardServiceImpl getOneBoard()");
		return replyBoardMapper.getOneBoard(num);
	}

	@Override
	public void reWriteInsert(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardServiceImpl reWriteInsert()");
		replyBoardMapper.reWriteInsert(rdto);
		
	}

	@Override
	public void reSqUpdate(ReplyBoardDTO rdto) {
		System.out.println("ReplyBoardServiceImpl reSqUpdate()");
		replyBoardMapper.reSqUpdate(rdto);
	}

	@Override
	public void replyProcess(ReplyBoardDTO rdto) {
		// 반드시 update메서드 먼저 실행해야함!
		replyBoardMapper.reSqUpdate(rdto);
		// 다음으로 insert메서드 실행
		replyBoardMapper.reWriteInsert(rdto);
		
	}
	
}

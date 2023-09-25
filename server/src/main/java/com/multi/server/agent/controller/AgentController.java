package com.multi.server.agent.controller;


import com.multi.server.agent.dto.RegistAgentRequestDTO;
import com.multi.server.agent.service.AgentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/agent")
public class AgentController {
//    에이전트(등록x)
//
//    디비에 저장이 안되있으니까 데이터를 요청을 못받고 못보냄
//
//    사용자는 에이전트의 ip를 알고있음 with 포트번호
//
//    사용자 -> 중앙서버
//    ip를 줄테니까 니가 에이전트에 등록 연락해라
//
//    중앙서버 -> 에이전트
//    등록해주세요 요청 보냄
//
//1. 에이전트 실행중
//-> 등록하는거 보냄
//
//2. 에이전트 미실행중
//-> time out 등 으로 실패
//
//1-1 성공했으니까
//    에이전트가 서버에 정보보냄
//
//2-1 실패했으니가
//            안보냄

    private final AgentService agentService;

    @GetMapping("/alive/{ip}")
    public ResponseEntity registRequest(@PathVariable String ip) { //ip를 받아서 에이전트 서버에 등록요청
        String[] agentInfo = agentService.callAgent(ip);
        RegistAgentRequestDTO dto = new RegistAgentRequestDTO(ip, agentInfo[0], agentInfo[1], agentInfo[2]);
        agentService.registAgent(dto);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/regist") //에이전트가 보내온 정보를 DB에 등록
//    public ResponseEntity<RegistAgentRequestDTO> regist(@RequestBody RegistAgentRequestDTO dto) {
//
//        agentService.registAgent(dto);
//        return ResponseEntity.ok()
//                .body(dto);
//    }
}

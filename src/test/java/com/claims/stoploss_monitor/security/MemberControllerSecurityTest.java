package com.claims.stoploss_monitor.security;

import com.claims.stoploss_monitor.members.entity.Member;
import com.claims.stoploss_monitor.members.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @WithMockUser(
            username = "brokerA",
            roles = "BROKER"
    )
    void brokerAShouldAccessGAMember()
            throws Exception {

        Member member =
                Member.builder()
                        .id(UUID.randomUUID())
                        .memberId("A-0001")
                        .groupId("GA")
                        .build();

        when(
                memberRepository.findByMemberId(
                        "A-0001"
                )
        ).thenReturn(
                Optional.of(member)
        );

        mockMvc.perform(
                        get(
                                "/api/members/A-0001/summary"
                        )
                )
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    @WithMockUser(
            username = "brokerA",
            roles = "BROKER"
    )
    void brokerAShouldNotAccessGBMember()
            throws Exception {

        Member member =
                Member.builder()
                        .id(UUID.randomUUID())
                        .memberId("B-0005")
                        .groupId("GB")
                        .build();

        when(
                memberRepository.findByMemberId(
                        "B-0005"
                )
        ).thenReturn(
                Optional.of(member)
        );

        mockMvc.perform(
                        get(
                                "/api/members/B-0005/summary"
                        )
                )
                .andExpect(
                        status().isForbidden()
                );
    }
}

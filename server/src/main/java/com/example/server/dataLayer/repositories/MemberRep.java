package com.example.server.dataLayer.repositories;

import com.example.server.businessLayer.Market.Users.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRep extends JpaRepository<Member, String> {
}

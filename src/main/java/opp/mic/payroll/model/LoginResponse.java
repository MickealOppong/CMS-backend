package opp.mic.payroll.model;

import lombok.Builder;

@Builder
public record LoginResponse (AppUser appUser,String accessToken,String token){
}

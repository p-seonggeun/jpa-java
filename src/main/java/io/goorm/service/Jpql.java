package io.goorm.service;

import io.goorm.model.Member;
import io.goorm.util.JPAConnectionUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class Jpql {
    public void createMember() {

        EntityManager em = JPAConnectionUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            // 단순 조회
            String jpql = "SELECT m FROM Member m WHERE m.memberName = :name";
            List<Member> result = em.createQuery(jpql, Member.class)
                    .setParameter("name", "김철수")
                    .getResultList();

            System.out.println(result);

            // 집계 함수 사용
            String jpql2 = "SELECT COUNT(m) FROM Member m";
            Long count = em.createQuery(jpql2, Long.class)
                    .getSingleResult();

            System.out.println(count);

            // 페이징 처리
            String jpql3 = "SELECT m FROM Member m ORDER BY m.memberName DESC";
            List<Member> result2 = em.createQuery(jpql3, Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println(result2);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // 트랜잭션 롤백
            }
            e.printStackTrace(); // 예외 출력
        } finally {
            em.close();
        }

    }
}

package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Candidate;
import com.repository.CandidateRepository;

@Service
public class CandidateService {

    private final CandidateRepository cndrepo;

    @Autowired
    public CandidateService(CandidateRepository cndrepo) {
        this.cndrepo = cndrepo;
    }

    public void addCandidate(Candidate candidate) {
        cndrepo.save(candidate);
    }

    public List<Candidate> getAllCandidates() {
        return cndrepo.findAll();
    }

    public Candidate getCandidateById(int id) {
        Optional<Candidate> candidate = cndrepo.findById(id);
        return candidate.orElseThrow(() -> new RuntimeException("Candidate not found with id: " + id));
    }

    public void deleteCandidate(int id) {
        cndrepo.deleteById(id);
    }

    public Candidate getCandidateByUser(String email) {
        return cndrepo.getCandidateByUser(email);
    }

}

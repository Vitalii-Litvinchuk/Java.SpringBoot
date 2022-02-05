package app.controllers;

import app.entities.Badge;
import app.entities.DTOs.Badge.BadgeDTO;
import app.mapper.BadgeMapper;
import app.repositories.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/badge")
public class BadgeController {
    private final BadgeRepository badgeRepository;
    private final BadgeMapper badgeMapper;

    @GetMapping("/get-all-badges")
    public Page<Badge> index() {
        long time = System.nanoTime();
        Pageable pageable;
        for (int i = 0; i <= 35; i++) {
            pageable  = PageRequest.of(i, 1_000_000);;
            Page<Badge> list = badgeRepository.findAll(pageable);
            long result = System.nanoTime() - time;
            double second = result / 1_000_000_000.0;
            System.out.println((System.nanoTime() - time) + "ns per million");
            System.out.println(second + "seconds");
        }
        pageable = PageRequest.of(0, 10);
        return badgeRepository.findAll(pageable);
    }
}

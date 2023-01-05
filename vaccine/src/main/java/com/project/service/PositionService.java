package com.project.service;

import com.project.entity.Position;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionService {
    /**
     * @return
     */
    List<Position> getAllPosition();
}

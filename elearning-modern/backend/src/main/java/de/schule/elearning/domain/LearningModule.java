package de.schule.elearning.domain;

import java.util.List;

public record LearningModule(
    String id,
    String title,
    String level,
    String focus,
    String info,
    String task,
    List<String> steps,
    String codeLang,
    String code,
    String transfer
) {}

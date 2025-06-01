package goormton.backend.sodamsodam.domain.review.enums;

public enum ReviewTag {
    a("조용해요"),
    b("깨끗해요"),
    c("친절해요");

    private final String description;

    ReviewTag(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

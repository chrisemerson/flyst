package uk.co.cemerson.flyst.entity;

public enum InstructorCategory
{
    NOT_AN_INSTRUCTOR(0),
    BASIC_INSTRUCTOR(1),
    ASSISTANT_CATEGORY_INSTRUCTOR(2),
    FULL_CATEGORY_INSTRUCTOR(3);

    private int mInstructorRank;

    InstructorCategory(int instructorRank)
    {
        mInstructorRank = instructorRank;
    }

    public static InstructorCategory fromRank(int rank)
    {
        for (InstructorCategory category : InstructorCategory.values()) {
            if (category.getInstructorRank() == rank) {
                return category;
            }
        }

        return null;
    }

    public int getInstructorRank()
    {
        return mInstructorRank;
    }
}

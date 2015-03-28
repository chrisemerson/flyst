package uk.co.cemerson.flyst.entity;

public enum InstructorCategory
{
    NOT_AN_INSTRUCTOR(0, "Not An Instructor"),
    BASIC_INSTRUCTOR(1, "Basic Instructor"),
    ASSISTANT_CATEGORY_INSTRUCTOR(2, "Assistant Category Instructor"),
    FULL_CATEGORY_INSTRUCTOR(3, "Full Category Instructor");

    private int mInstructorRank;
    private String mLabel;

    InstructorCategory(int instructorRank, String label)
    {
        mInstructorRank = instructorRank;
        mLabel = label;
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

    @Override
    public String toString()
    {
        return mLabel;
    }
}

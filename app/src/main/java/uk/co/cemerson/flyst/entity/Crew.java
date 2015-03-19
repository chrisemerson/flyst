package uk.co.cemerson.flyst.entity;

public class Crew
{
    private Pilot mP1;
    private Pilot mP2;

    public Pilot getP1()
    {
        return mP1;
    }

    public void setP1(Pilot p1)
    {
        mP1 = p1;
    }

    public Pilot getP2()
    {
        return mP2;
    }

    public void setP2(Pilot p2)
    {
        mP2 = p2;
    }
}

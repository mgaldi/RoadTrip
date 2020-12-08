import java.util.Objects;
import java.util.StringJoiner;

public class Road {

    private String from, to;
    private int milesDist, minutesDist;

    public Road () { }

    public String getFrom() {
        return this.from;
    }

    public Road setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return this.to;
    }

    public Road setTo(String to) {
        this.to = to;
        return this;
    }

    public int getMilesDist() {
        return this.milesDist;
    }

    public Road setMilesDist(int milesDist) {
        this.milesDist = milesDist;
        return this;
    }

    public double getMinutesDist() {
        return this.minutesDist;
    }

    public Road setMinutesDist(int minutesDist) {
        this.minutesDist = minutesDist;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Road.class.getSimpleName() + "{", "}")
                .add("from=" + this.from)
                .add("to=" + this.to)
                .add("milesDist=" + this.milesDist)
                .add("minutesDist=" + this.minutesDist)
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || this.getClass() != other.getClass())
            return false;
        Road road = (Road) other;
        return  Double.compare(road.milesDist, this.milesDist) == 0 &&
                Double.compare(road.minutesDist, this.minutesDist) == 0 &&
                Objects.equals(this.from, road.from) &&
                Objects.equals(this.to, road.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.from, this.to, this.milesDist, this.minutesDist);
    }

}

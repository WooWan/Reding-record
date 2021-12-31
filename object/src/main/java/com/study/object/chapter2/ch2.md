# 객체지향 프로그래밍

### 올바른 객체지향 프로그래밍을 설계하기 위해서

> 객체 지향의 본질은 단순히 클래스가 아닌 객체에 초점을 맞추어야 한다.

1. 실제 비즈니스가 어떤 객체가 필요한지, 그 객체가 어떤 상태와 행동을 가져야 하는지 정의하는 것이 먼저다.
2. 객체는 독립적인 존재가 아닌 협력하는 공동체의 일원이다. 객체는 메세지를 통해 다른 객체와 협력하여 설계를 유연하게 확장한다.


#### 어떤 부분을 외부에 공개하고 어떤 부분을 감출 것인가?

* 우리는 1장에서 살펴보았듯이 외부에 속성을 공개해서 자유롭게 수정하게 되면, 강한 의존성과 결합성이 생긴다는 것을 보았다. 외부에서 직접적으로 속성을 변경하지 못하게 속성들을 수정하지 못하게 자바는 접근 제한자를 지원한다(private, protected, public). 속성은 외부에서 변경하지 못하도록 private으로, 메세지를 주고 받을 함수들을 publi으로 구현해 다른 객체와 상호작용할 수 있도록 만든다.
``` java
public class Screening {

    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    public LocalDateTime getStartTime() {
        return whenScreened;
    }
    // Reservation 에서 구현하는 것이 더 자연스럽지 않을까?
    public Reservation reserve(Customer customer, int audienceCount) {
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    public Money calculateFee(int audienceCount) {
        return movie.calculateMovieFee(this).times(audienceCount);
    }

    public boolean isSequence(int sequence) {
        return this.sequence == sequence;
    }

    public Money getMovieFee() {
        return movie.getFee();
    }
}
```
Screening 클래스는 movie, sequence, whenScreened와 같은 속성들을 private으로 외부와 메세지를 주고받는 함수들을 public으로 선언하였다.

>* 객체의 내부와 외부를 잘 구분한다면?
1. 다른 외부 객체와의 의존성을 약하게 결합하여 유지보수가 용이해진다
2. 다른 클래스의 기능을 개발할때, 외부로부터 클래스의 일부를 감춤으로써, 알아야 할 지식의 양이 줄어들고, 객체의 자율성이 높아진다.
3. 의미를 명확하게 해준다.
* 예를 들어, Money를 단순히 primitive타입으로 계산할 수도 있지만, class를 만들어줌으로써, 이 로직이 Money에 관한 로직임을 명시적으로 특정할 수 있다.


### 다형성과 상속, 추상화

``` java
public class Movie{
    public void changeDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}

```
할인 정책은 일정 금액을 할인해주는 AmountDiscountPolicy와, 비율로 할인을 해주는 PercentDiscountPolicy가 존재한다. 하지만, 위의 Movie 클래스는 어떤 할인 정책으로 결정되는지 전혀 알지 못한다. 이것은 **다형성**의 강력한 기능 덕분이다.

* 한가지 먼저 알아야 할 점은 설계상의 의존관계와 (compile 시점)과 실행시점의 의존관계가 다를 수 있다는 점이다.


``` java
        new Movie("타이타닉",
                Duration.ofMinutes(180),
                Money.wons(11000),
                new PercentDiscountPolicy(0.1,
                        new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
                        new SequenceCondition(2),
                        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))));

        new Movie("아바타",
                Duration.ofMinutes(180),
                Money.wons(11000),
                new PercentDiscountPolicy(1000,
                        new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
                        new SequenceCondition(2),
                        new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))));
```
위의 코드와 같이 Movie를 만들어줄때, 외부에서 의존성이 결정되며, runtime 시점에, AmountDiscountPolicy와 PercentDiscountPolicy가 지연 바인딩(lazy binding)을 통해 업캐스팅되어 다른 객체와 협력한다.

* 다형성으로 인해, 우리는 쉽게 코드를 재사용할 수 있고, 낮은 의존성을 달성할 수 있었다. 하지만 코드의 의존성과 런타임의 의존성이 다르면 코드를 이해하기 어렵고, 우리는 이 트레이드 오프를 잘 이해하고 적용시켜야 한다.

### 상속과 인터페이스

인터페이스의 가장 큰 가치는 메서드, 변수의 재사용도 있지만, 상속을 통해 부모 클래스와 의존성을 가지는 객체와 협력할 수 있다는 점이다.

#### 추상 클래스와 인터페이스

``` java
public abstract class DiscountPolicy {

    private List<DiscountCondition> conditions = new ArrayList<>();

    public DefaultDiscountPolicy(DiscountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }


    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }
        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening screening);
}

public class NoneDiscountPolicy extends DiscountPolicy {

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
```
위와 같이 할인 정책이 없는 영화도 존재할 수 있다. 이럴 경우에는 NoneDiscountPolicy를 만들어서 의미를 명확하게 하는 것이 좋아보인다. 하지만, NoneDiscountPolicy에서 calcualteDiscountAmount가 어떻게 구현되는지에 상관없이 부모 클래스의 calculateDiscountAmount에 의해 정의되는 것을 알 수 있는데, 이를 해결하기 위해 abstract method대신 interface로 변경함으로써 해결할 수 있다.

``` java
public interface DiscountPolicy {
    Money calculateDiscountAmount(Screening screening);
}

public class NoneDiscountPolicy implements DiscountPolicy {

    @Override
    public Money calculateDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}

public abstract class DefaultDiscountPolicy implements DiscountPolicy {

    private List<DiscountCondition> conditions = new ArrayList<>();

    public DefaultDiscountPolicy(DiscountCondition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }


    public Money calculateDiscountAmount(Screening screening) {
        for (DiscountCondition each : conditions) {
            if (each.isSatisfiedBy(screening)) {
                return getDiscountAmount(screening);
            }
        }
        return Money.ZERO;
    }

    abstract protected Money getDiscountAmount(Screening screening);
}

```
기존 클래스를 DefaultDiscountPolicy로 변경시킨 후, 인터페이스를 상속받고, NoneDiscountPolicy가 DiscountPolicy를 상속받게 해, calculateDiscountAmount를 오버라이딩하는 방법으로 해결할 수 있다. 하지만 코드 동작의 결과는 똑같은데, 실제 비즈니스에서는 같이 협업하는 팀원들과 트레이드 오프를 인지하고 협의하는 방법이 가장 좋아보인다.
package jvm.language.compare.java.cards;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.impl.factory.Sets;

public class Card implements Comparable<Card>
{
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    public static LazyIterable<Card> getCards()
    {
        return Sets.cartesianProduct(
                EnumSet.allOf(Rank.class),
                EnumSet.allOf(Suit.class),
                Card::new);
    }

    public static Stream<Card> streamCards()
    {
        return Card.cartesianProduct(
                EnumSet.allOf(Rank.class),
                EnumSet.allOf(Suit.class),
                Card::new);
    }

    private static <A, B, C> Stream<C> cartesianProduct(Set<A> set1, Set<B> set2, Function2<A, B, C> function)
    {
        return set1.stream().flatMap(first ->
                set2.stream().map(second -> function.apply(first, second)));
    }

    public Rank getRank()
    {
        return this.rank;
    }

    public Suit getSuit()
    {
        return this.suit;
    }

    @Override
    public int compareTo(Card o)
    {
        return Comparator.comparing(Card::getSuit)
                .thenComparing(Card::getRank)
                .compare(this, o);
    }

    public boolean isDiamonds()
    {
        return this.suit == Suit.DIAMONDS;
    }

    public boolean isHearts()
    {
        return this.suit == Suit.HEARTS;
    }

    public boolean isSpades()
    {
        return this.suit == Suit.SPADES;
    }

    public boolean isClubs()
    {
        return this.suit == Suit.CLUBS;
    }

    public boolean isSameRank(Rank rank)
    {
        return this.rank == rank;
    }

    public boolean isSameSuit(Suit suit)
    {
        return this.suit == suit;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object instanceof Card)
        {
            Card card = (Card) object;
            return this.isSameSuit(card.suit) && this.isSameRank(card.rank);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return 31 * rank.hashCode() + suit.hashCode();
    }

    @Override
    public String toString()
    {
        return this.rank + " of " + this.suit;
    }
}

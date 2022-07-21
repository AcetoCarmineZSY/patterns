package fr.uvsq.hal.pglp.patterns;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * La classe <code>Team</code> représente un groupe dans une organisation.
 *
 * @author hal
 * @version 2022
 */
public class Team implements Element, Iterable<Element> {
  private List<Element> members;

  public Team() {
    members = new ArrayList<>();
  }

  public void add(Element element) {
    members.add(element);
  }

  public boolean contains(Element element) {
    return members.contains(element);
  }

  @Override
  public Iterator<Element> iterator() {
    return new TeamIterator(members);
  }

  private static class TeamIterator implements Iterator<Element> {
    private Stack<Iterator<Element>> iteratorStack;

    public TeamIterator(List<Element> members) {
      iteratorStack = new Stack<>();
      iteratorStack.push(members.iterator());
    }

    @Override
    public boolean hasNext() {
      boolean hasNext = iteratorStack.peek().hasNext();
      while (!hasNext) {
        iteratorStack.pop();
        if (iteratorStack.isEmpty()) {
          return false;
        }
        hasNext = iteratorStack.peek().hasNext();
      }
      return hasNext;
    }

    @Override
    public Element next() {
      Element nextElement = iteratorStack.peek().next();
      while (nextElement instanceof Team) {
        Team team = (Team) nextElement;
        Iterator<Element> newIterator = team.iterator();
        iteratorStack.push(newIterator);
        nextElement = iteratorStack.peek().next();
      }
      return nextElement;
    }
  }
}


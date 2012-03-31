package etlmail.engine.css;

class SelectorSpecificity(val ids: Int, val classes: Int, val types: Int) extends Ordered[SelectorSpecificity] {

  override def compare(o: SelectorSpecificity): Int = {
    var result = ids - o.ids;
    if (result == 0) {
      result = classes - o.classes;
    }
    if (result == 0) {
      result = types - o.types;
    }
    return result;
  }

  override def hashCode(): Int = {
    val prime = 31;
    var result = 1;
    result = (prime * result) + classes;
    result = (prime * result) + ids;
    result = (prime * result) + types;
    return result;
  }

  override def equals(obj: Any): Boolean =
    (this eq obj.asInstanceOf[AnyRef]) || (obj match {
      case null => false
      case other: SelectorSpecificity =>
        classes == other.classes && ids == other.ids && types == other.types
      case _ => false
    })

  override def toString(): String = "SelectorSpecificit(" + ids + ", " + classes + ", " + types + ")"
}

object SelectorSpecificity {
  class Builder {
    private var ids: Int = 0
    private var classes: Int = 0
    private var types: Int = 0

    def addId(count: Int): Builder = {
      ids += count;
      return this;
    }

    def addClass(count: Int): Builder = {
      classes += count;
      return this;
    }

    def addType(count: Int): Builder = {
      types += count;
      return this;
    }

    def asSpecificity(): SelectorSpecificity = new SelectorSpecificity(ids, classes, types)
  }
}
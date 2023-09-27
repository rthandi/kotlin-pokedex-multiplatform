import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Pokemon (
  @SerialName("id")
  val id: Long,
  @SerialName("name")
  val name: String,
//  @SerialName("type")
//  val type: String?
) {
  override fun toString(): String {
    return "Pokemon: ${this.name}"
  }
}
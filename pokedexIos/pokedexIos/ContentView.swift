import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    @State private var pokemonName: String = ""

    var body: some View {
        List(viewModel.phrases, id: \.self) { phrase in
            Text(phrase)
        }
        Button("Create", action: { Task {
          do {
              try await Greeting().createPokemon(name: $pokemonName.wrappedValue, type: "fire")
          } catch {
            print("sad times")
          }
        } })
        TextField("Pokemon name", text: $pokemonName)
    }
}

extension ContentView {
    class ViewModel: ObservableObject {
        @Published var phrases: [String] = ["Loading..."]
        init() {
            Greeting().fetchPokemon { greeting, error in
                DispatchQueue.main.async {
                    if let greeting = greeting {
                        self.phrases = greeting
                    } else {
                        self.phrases = [error?.localizedDescription ?? "error"]
                    }
                }
            }
        }
    }

}

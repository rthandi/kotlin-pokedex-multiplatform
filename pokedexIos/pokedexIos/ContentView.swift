import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel

    var body: some View {
        List(viewModel.phrases, id: \.self) { phrase in
            Text(phrase)
        }
        Button("Create", action: { Task {
          do {
            await try Greeting().createPokemon(name: "ios", type: "fire")
          } catch {
            print("sad times")
          }
        } })
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

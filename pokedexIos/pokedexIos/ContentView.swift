import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel

    var body: some View {
        List(viewModel.phrases, id: \.self) { phrase in
            Text(phrase)
        }
    }
}

extension ContentView {
    class ViewModel: ObservableObject {
        @Published var phrases: [String] = ["Loading..."]
        init() {
            Greeting().greet { greeting, error in
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

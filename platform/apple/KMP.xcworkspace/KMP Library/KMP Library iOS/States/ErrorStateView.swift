import SwiftUI

struct ErrorStateView: View {
    let error: String
    let onRefresh: () -> Void

    var body: some View {
        VStack {
            Text("Error: \(error)")
                .foregroundColor(.red)
                .font(.body)

            Button("Refresh") {
                onRefresh()
            }
            .padding(.top, 12)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
